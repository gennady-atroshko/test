package mydeposits_hybris.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object metrics_mydeposits_hybris_vcs : GitVcsRoot({
    uuid = "a5fbeb18-201d-4850-810c-0f81df36e32d"
    extId = "metrics_mydeposits_hybris_vcs"
    name = "metrics_mydeposits_hybris_vcs"
    url = "git@github.com:gennady-atroshko/test.git"
    agentCleanPolicy = GitVcsRoot.AgentCleanPolicy.ALWAYS
    authMethod = uploadedKey {
        uploadedKey = "mydeposits_hybris_CI"
    }
})
