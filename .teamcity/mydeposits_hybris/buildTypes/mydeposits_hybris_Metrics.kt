package mydeposits_hybris.buildTypes

import mydeposits_hybris.vcsRoots.metrics_mydeposits_hybris_vcs
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.GradleBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.GradleBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.CommitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.CommitStatusPublisher.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.schedule

object mydeposits_hybris_Metrics : BuildType({
    template(mydeposits_hybris_Template)
    uuid = "ec48f6a5-0299-469d-a6ac-041907db9f23"
    extId = "mydeposits_hybris_Metrics"
    name = "Metrics"

    vcs {
        root(mydeposits_hybris.vcsRoots.metrics_mydeposits_hybris_vcs)
    }

    steps {
        stepsOrder = arrayListOf("POM_EXTENDER", "maven_RUNNER", "maven_METRICS")
    }

    triggers {
        vcs {
            id = "vcsTrigger"
            perCheckinTriggering = true
        }
    }

    disableSettings(
            "gradle_RUNNER", "gradle_METRICS", "BUILD_EXT_STATUS"
    )

    features {
        commitStatusPublisher {
            id = "BUILD_EXT_STATUS_METRICS"
            vcsRootExtId = metrics_mydeposits_hybris_vcs.extId
                        publisher = github {
                githubUrl = "https://api.github.com"
                authType = password {
                    userName = ""
                    password = ""
                }
            }
        }
    }
})
