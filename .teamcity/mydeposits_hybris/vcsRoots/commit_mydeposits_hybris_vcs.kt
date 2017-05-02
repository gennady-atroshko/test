package mydeposits_hybris.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object commit_mydeposits_hybris_vcs : GitVcsRoot({
    uuid = "b5dc8380-9ac9-4b8e-ae8b-327e0597efcb"
    extId = "commit_mydeposits_hybris_vcs"
    name = "commit_mydeposits_hybris_vcs"
    url = "git@github.com:gennady-atroshko/test.git"
    branchSpec = "+:refs/heads/*"
    agentCleanPolicy = GitVcsRoot.AgentCleanPolicy.ALWAYS
    authMethod = uploadedKey {
        uploadedKey = "mydeposits_hybris_CI"
    }
})
