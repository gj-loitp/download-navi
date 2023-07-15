package com.roy.downloader.core.system

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.system.Os
import com.roy.downloader.core.system.SafFileSystem.FakePath
import java.io.FileDescriptor
import java.io.FileNotFoundException
import java.io.IOException

internal class SafFsModule(private val appContext: Context) : FsModule {
    override fun getName(filePath: Uri): String? {
        val fs = SafFileSystem.getInstance(appContext)
        val stat = fs.stat(filePath)
        return stat?.name
    }

    override fun getDirName(dir: Uri): String? {
        val stat = SafFileSystem.getInstance(appContext).statSafRoot(dir)
        return if (stat?.name == null) dir.path else stat.name
    }

    override fun getFileUri(dir: Uri, fileName: String, create: Boolean): Uri? {
        return SafFileSystem.getInstance(appContext).getFileUri(dir, fileName, create)
    }

    override fun getFileUri(relativePath: String, dir: Uri, create: Boolean): Uri? {
        return SafFileSystem.getInstance(appContext)
            .getFileUri(FakePath(dir, relativePath), create)
    }

    @Throws(FileNotFoundException::class)
    override fun delete(filePath: Uri): Boolean {
        val fs = SafFileSystem.getInstance(appContext)
        return fs.delete(filePath)
    }

    override fun exists(filePath: Uri): Boolean {
        val fs = SafFileSystem.getInstance(appContext)
        return fs.exists(filePath)
    }

    override fun openFD(path: Uri): FileDescriptorWrapper {
        return FileDescriptorWrapperImpl(appContext, path)
    }

    @Throws(IOException::class)
    override fun getDirAvailableBytes(dir: Uri): Long {
        var availableBytes: Long = -1
        val contentResolver = appContext.contentResolver
        val fs = SafFileSystem.getInstance(appContext)
        val dirPath = fs.makeSafRootDir(dir)
        contentResolver.openFileDescriptor(dirPath, "r").use { pfd ->
            if (pfd == null) return availableBytes
            availableBytes = getAvailableBytes(pfd.fileDescriptor)
        }
        return availableBytes
    }

    override fun getFileSize(filePath: Uri): Long {
        val fs = SafFileSystem.getInstance(appContext)
        val stat = fs.stat(filePath)
        return stat?.length ?: -1
    }

    override fun takePermissions(path: Uri) {
        val resolver = appContext.contentResolver
        val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        resolver.takePersistableUriPermission(path, takeFlags)
    }

    override fun getDirPath(dir: Uri): String? {
        val stat = SafFileSystem.getInstance(appContext).statSafRoot(dir)
        return if (stat?.name == null) dir.path else stat.name
    }

    /*
     * Return the number of bytes that are free on the file system
     * backing the given FileDescriptor
     *
     * TODO: maybe there is analog for KitKat?
     */
    @Throws(IOException::class)
    private fun getAvailableBytes(fd: FileDescriptor): Long {
        return try {
            val stat = Os.fstatvfs(fd)
            stat.f_bavail * stat.f_bsize
        } catch (e: Exception) {
            throw IOException(e)
        }
    }

    override fun mkdirs(dir: Uri, relativePath: String): Boolean {
        val fs = SafFileSystem.getInstance(appContext)
        return fs.mkdirs(FakePath(dir, relativePath))
    }
}
