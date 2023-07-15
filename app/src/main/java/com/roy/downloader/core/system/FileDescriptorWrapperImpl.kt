package com.roy.downloader.core.system

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.FileDescriptor
import java.io.FileNotFoundException
import java.io.IOException

internal class FileDescriptorWrapperImpl(appContext: Context, path: Uri) : FileDescriptorWrapper {
    private val contentResolver: ContentResolver
    private val path: Uri
    private var pfd: ParcelFileDescriptor? = null

    init {
        contentResolver = appContext.contentResolver
        this.path = path
    }

    @Throws(FileNotFoundException::class)
    override fun open(mode: String): FileDescriptor? {
        pfd = contentResolver.openFileDescriptor(path, mode)
        return if (pfd == null) null else pfd?.fileDescriptor
    }

    @Throws(IOException::class)
    override fun close() {
        pfd?.close()
    }
}
