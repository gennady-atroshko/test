package mydeposits_hybris.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.GradleBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.GradleBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object mydeposits_hybris_Develop : BuildType({
    template(mydeposits_hybris_Template)
    uuid = "869c451c-b51d-49bc-aca2-8d9e978f94af"
    extId = "mydeposits_hybris_Develop"
    name = "Develop"

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
