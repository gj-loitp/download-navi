package com.roy.downloader.core.system

import android.system.Os
import android.system.OsConstants
import java.io.FileDescriptor
import java.io.IOException

internal class SysCallImpl : SysCall {
    @Throws(IOException::class, UnsupportedOperationException::class)
    override fun lseek(fd: FileDescriptor, offset: Long) {
        try {
            Os.lseek(fd, offset, OsConstants.SEEK_SET)
        } catch (e: Exception) {
            throw IOException(e)
        }
    }

    @Throws(IOException::class)
    override fun fallocate(fd: FileDescriptor, length: Long) {
        try {
            val curSize = Os.fstat(fd).st_size
            val newBytes = length - curSize
            val availBytes = availableBytes(fd)
            if (availBytes < newBytes) throw IOException(
                "Not enough free space; " + newBytes + " requested, " +
                        availBytes + " available"
            )
            Os.posix_fallocate(fd, 0, length)
        } catch (e: Exception) {
            try {
                Os.ftruncate(fd, length)
            } catch (ex: Exception) {
                throw IOException(ex)
            }
        }
    }

    /*
     * Return the number of bytes that are free on the file system
     * backing the given FileDescriptor
     *
     * TODO: maybe there is analog for KitKat?
     */
    @Throws(IOException::class)
    override fun availableBytes(fd: FileDescriptor): Long {
        return try {
            val stat = Os.fstatvfs(fd)
            stat.f_bavail * stat.f_bsize
        } catch (e: Exception) {
            throw IOException(e)
        }
    }
}
