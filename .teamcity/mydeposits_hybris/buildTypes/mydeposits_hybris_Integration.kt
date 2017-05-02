package mydeposits_hybris.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.GradleBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.GradleBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object mydeposits_hybris_Integration : BuildType({
    template(mydeposits_hybris_Template)
    uuid = "46d24537-0215-4e72-84f8-cdb0dee3db19"
    extId = "mydeposits_hybris_Integration"
    name = "Integration"

    vcs {
        root(mydeposits_hybris.vcsRoots.commit_mydeposits_hybris_vcs)
    }

    steps {
        stepsOrder = arrayListOf("POM_EXTENDER", "maven_RUNNER")
    }

    triggers {
        vcs {
            id = "vcsTrigger"
            perCheckinTriggering = true
        }
    }

    disableSettings(
            "gradle_RUNNER", "FINGBUGS_EXT", "PMD_EXT", "maven_METRICS", "gradle_METRICS"
    )
})
