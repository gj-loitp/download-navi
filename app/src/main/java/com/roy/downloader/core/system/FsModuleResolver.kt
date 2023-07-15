package com.roy.downloader.core.system

import android.net.Uri

/*
 * An FsModule provider.
 */
internal interface FsModuleResolver {
    fun resolveFsByUri(uri: Uri): FsModule?
}
