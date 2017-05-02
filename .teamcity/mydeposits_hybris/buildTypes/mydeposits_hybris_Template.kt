package mydeposits_hybris.buildTypes


import mydeposits_hybris.vcsRoots.commit_mydeposits_hybris_vcs
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.BuildStep
import jetbrains.buildServer.configs.kotlin.v10.BuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.CommitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.CommitStatusPublisher.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ExecBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ExecBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.exec
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v10.failureConditions.BuildFailureOnMetric
import jetbrains.buildServer.configs.kotlin.v10.failureConditions.BuildFailureOnMetric.*
import jetbrains.buildServer.configs.kotlin.v10.failureConditions.failOnMetricChange

object mydeposits_hybris_Template : Template({
    uuid = "596f85c4-de6d-4266-8296-22ac912d508e"
    extId = "mydeposits_hybris_Template"
    name = "Template"

    enablePersonalBuilds = false

    vcs {
        checkoutMode = CheckoutMode.ON_AGENT
    }

    artifactRules = """
        target/site
    """.trimIndent()

    steps {
        gradle {
            name = "Gradle build"
            id = "gradle_RUNNER"
            tasks = "clean build"
            useGradleWrapper = true
        }

        gradle {
            name = "Gradle metrics"
            id = "gradle_METRICS"
            tasks = "clean build"
            useGradleWrapper = true
            param("teamcity.coverage.idea.includePatterns", "com.*\norg.*")
            param("teamcity.coverage.runner", "IDEA")
        }

        script {
            name = "Extend POM"
            id = "POM_EXTENDER"
            scriptContent = """
                for file in $(find . | grep "pom.xml"); do
                  java -jar /opt/teamcity_extras/pom-extender/target/extender-1.0-SNAPSHOT-jar-with-dependencies.jar ${'$'}file ${'$'}file
                done
            """
        }

        maven {
            name = "Maven build"
            id = "maven_RUNNER"
            goals = "clean package"
            mavenVersion = bundled_3_3()
            param("target.jdk.home", "%env.JDK_18_x64%")
            param("teamcity.coverage.idea.includePatterns", "com.*\norg.*")
            param("teamcity.coverage.runner", "IDEA")
        }

        maven {
            name = "Maven metrics"
            id = "maven_METRICS"
            goals = "site"
            mavenVersion = bundled_3_3()
            param("target.jdk.home", "%env.JDK_18_x64%")
        }
    }

    features {
        commitStatusPublisher {
            id = "BUILD_EXT_STATUS"
            vcsRootExtId = commit_mydeposits_hybris_vcs.extId
                        publisher = github {
                githubUrl = "https://api.github.com"
                authType = password {
                userName = "gennady-atroshko"
                password = "zxxdaf609ba98d51f135e5a735392ae1669416baa0aa2b207e05226bd5c0a941d28ae50955d0857863f775d03cbe80d301b"
                }
            }
        }

        feature {
            id = "CHECKTYLE_EXT"
            type = "xml-report-plugin"
            param("xmlReportParsing.reportDirs", "**/target/**/checkstyle-result.xml\n**/build/**/checkstyle/main.xml")
            param("xmlReportParsing.reportType", "checkstyle")
        }

        feature {
            id = "FINGBUGS_EXT"
            type = "xml-report-plugin"
            param("xmlReportParsing.findBugs.home", "/opt/teamcity_extras/findbugs-3.0.1")
            param("xmlReportParsing.reportDirs", "**/target/**/findbugsXml.xml\n**/build/**/findbugs/main.xml")
            param("xmlReportParsing.reportType", "findBugs")
        }

        feature {
            id = "PMD_EXT"
            type = "xml-report-plugin"
            param("xmlReportParsing.reportDirs", "**/target/**/pmd.xml\n**/build/**/pmd/main.xml")
            param("xmlReportParsing.reportType", "pmd")
        }

        feature {
            id = "PMD_CPD_EXT"
            type = "xml-report-plugin"
            param("xmlReportParsing.reportDirs", "**/target/**/cpd.xml")
            param("xmlReportParsing.reportType", "pmdCpd")
        }
    }

    failureConditions {
        failOnMetricChange {
            id = "BUILD_FAILURE"
            metric = BuildFailureOnMetric.MetricType.INSPECTION_ERROR_COUNT
            threshold = 0
            units = BuildFailureOnMetric.MetricUnit.DEFAULT_UNIT
            comparison = BuildFailureOnMetric.MetricComparison.MORE
            param("anchorBuild", "lastSuccessful")
        }
    }
})