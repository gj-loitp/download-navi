package com.roy.downloader.core.system

import android.content.Context
import android.net.Uri
import com.roy.downloader.core.utils.Utils

internal class FsModuleResolverImpl(private val appContext: Context) : FsModuleResolver {
    private val safModule: SafFsModule = SafFsModule(appContext)
    private val defaultModule: DefaultFsModule = DefaultFsModule(appContext)

    override fun resolveFsByUri(uri: Uri): FsModule? {
        return if (Utils.isSafPath(
                appContext,
                uri
            )
        ) safModule else if (Utils.isFileSystemPath(uri)) defaultModule else throw IllegalArgumentException(
            "Cannot resolve file system for the given uri: $uri"
        )
    }
}
