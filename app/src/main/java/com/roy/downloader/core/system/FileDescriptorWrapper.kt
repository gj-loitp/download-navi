package com.roy.downloader.core.system

import java.io.Closeable
import java.io.FileDescriptor
import java.io.FileNotFoundException

interface FileDescriptorWrapper : Closeable {
    @Throws(FileNotFoundException::class)
    fun open(mode: String): FileDescriptor?
}
