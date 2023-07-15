package com.roy.downloader.core.system

import java.io.FileDescriptor
import java.io.IOException

/*
 * A platform dependent interface for system calls.
 */
internal interface SysCall {
    @Throws(IOException::class, UnsupportedOperationException::class)
    fun lseek(fd: FileDescriptor, offset: Long)

    @Throws(IOException::class)
    fun fallocate(fd: FileDescriptor, length: Long)

    @Throws(IOException::class)
    fun availableBytes(fd: FileDescriptor): Long
}
