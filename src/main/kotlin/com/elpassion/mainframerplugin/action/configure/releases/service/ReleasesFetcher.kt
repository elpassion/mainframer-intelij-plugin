package com.elpassion.mainframerplugin.action.configure.releases.service

import com.elpassion.mainframerplugin.action.configure.releases.api.GitHubApi
import com.elpassion.mainframerplugin.action.configure.releases.api.ReleaseApiModel
import io.reactivex.Single

fun releasesFetcher(api: GitHubApi) = {
    api.listReleases()
            .mapWithoutVPrefix()
            .dropUnsupportedVersions()
}

private fun Single<List<String>>.dropUnsupportedVersions(): Single<List<String>> {
    val versionOneRegex = "1.\\d.\\d".toRegex()
    return map { it.filter { !it.matches(versionOneRegex) } }
}

private fun Single<List<ReleaseApiModel>>.mapWithoutVPrefix() = map { it.map { it.tagName.drop(1) } }
