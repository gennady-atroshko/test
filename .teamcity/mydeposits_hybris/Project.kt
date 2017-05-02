package mydeposits_hybris

import mydeposits_hybris.buildTypes.*
import mydeposits_hybris.vcsRoots.*
import mydeposits_hybris.vcsRoots.commit_mydeposits_hybris_vcs
import mydeposits_hybris.vcsRoots.metrics_mydeposits_hybris_vcs
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.Project
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.VersionedSettings.*
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.versionedSettings

object Project : Project({
    uuid = "aa74f9e7-fa61-4b25-a50d-f58c345da4da"
    extId = "mydeposits_hybris"
    parentId = "_Root"
    name = "mydeposits_hybris"

    vcsRoot(commit_mydeposits_hybris_vcs)
    vcsRoot(metrics_mydeposits_hybris_vcs)

    buildType(mydeposits_hybris_Develop)
    buildType(mydeposits_hybris_Integration)
    buildType(mydeposits_hybris_Metrics)

    template(mydeposits_hybris_Template)

    features {
        versionedSettings {
            id = "PROJECT_EXT_2"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.USE_CURRENT_SETTINGS
            rootExtId = metrics_mydeposits_hybris_vcs.extId
            showChanges = true
            settingsFormat = VersionedSettings.Format.KOTLIN
        }
        feature {
            id = "REPORT_ALL"
            type = "ReportTab"
            param("startPage", "project-reports.html")
            param("title", "All reports")
            param("type", "BuildReportTab")
        }
        feature {
            id = "REPORT_CHECKSTYLE"
            type = "ReportTab"
            param("startPage", "checkstyle.html")
            param("title", "Checkstyle")
            param("type", "BuildReportTab")
        }
        feature {
            id = "REPORT_JACOCO"
            type = "ReportTab"
            param("startPage", "jacoco/index.html")
            param("title", "JaCoCo")
            param("type", "BuildReportTab")
        }
        feature {
            id = "REPORT_FINDBUGS"
            type = "ReportTab"
            param("startPage", "findbugs.html")
            param("title", "FindBugs")
            param("type", "BuildReportTab")
        }
        feature {
            id = "REPORT_PMD"
            type = "ReportTab"
            param("startPage", "pmd.html")
            param("title", "PMD")
            param("type", "BuildReportTab")
        }
        feature {
            id = "REPORT_CPD"
            type = "ReportTab"
            param("startPage", "cpd.html")
            param("title", "CPD")
            param("type", "BuildReportTab")
        }
    }
})
