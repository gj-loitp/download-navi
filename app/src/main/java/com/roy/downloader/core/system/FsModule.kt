package com.roy.downloader.core.system

import android.net.Uri
import java.io.FileNotFoundException
import java.io.IOException

/*
 * A platform dependent filesystem interface, that uses in FileSystemFacade.
 */
internal interface FsModule {
    fun getName(filePath: Uri): String?

    /*
     * Returns path (if present) or directory name
     */
    fun getDirName(dir: Uri): String?

    /*
     * Returns Uri of the file by the given file name or
     * null if the file doesn't exists
     */
    @Throws(IOException::class)
    fun getFileUri(dir: Uri, fileName: String, create: Boolean): Uri?

    /*
     * Returns a file (if exists) Uri by relative path (e.g foo/bar.txt)
     * from the pointed directory
     */
    @Throws(IOException::class)
    fun getFileUri(relativePath: String, dir: Uri, create: Boolean): Uri?

    @Throws(FileNotFoundException::class)
    fun delete(filePath: Uri): Boolean
    fun exists(filePath: Uri): Boolean
    fun openFD(path: Uri): FileDescriptorWrapper?

    /*
     * Return the number of bytes that are free on the file system
     * backing the given Uri
     */
    @Throws(IOException::class)
    fun getDirAvailableBytes(dir: Uri): Long
    fun getFileSize(filePath: Uri): Long
    fun takePermissions(path: Uri)

    /*
     * Returns path (if present) or directory name
     */
    fun getDirPath(dir: Uri): String?
    fun mkdirs(dir: Uri, relativePath: String): Boolean
}
