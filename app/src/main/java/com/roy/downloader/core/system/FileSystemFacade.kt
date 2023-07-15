package com.roy.downloader.core.system

import android.net.Uri
import com.roy.downloader.core.exception.FileAlreadyExistsException
import java.io.Closeable
import java.io.File
import java.io.FileDescriptor
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

interface FileSystemFacade {
    @Throws(IOException::class)
    fun seek(fout: FileOutputStream, offset: Long)

    @Throws(IOException::class)
    fun allocate(fd: FileDescriptor, length: Long)
    fun closeQuietly(closeable: Closeable?)
    fun makeFilename(
        dir: Uri, desiredFileName: String
    ): String?

    @Throws(IOException::class, FileAlreadyExistsException::class)
    fun moveFile(
        srcDir: Uri, srcFileName: String, destDir: Uri, destFileName: String, replace: Boolean
    )

    @Throws(IOException::class)
    fun copyFile(
        srcFile: Uri, destFile: Uri, truncateDestFile: Boolean
    )

    fun getFD(path: Uri): FileDescriptorWrapper?
    val extensionSeparator: String?
    fun appendExtension(fileName: String, mimeType: String): String?
    val defaultDownloadPath: String?
    val userDirPath: String?

    @Throws(FileNotFoundException::class)
    fun deleteFile(path: Uri): Boolean
    fun getFileUri(
        dir: Uri, fileName: String
    ): Uri?

    fun getFileUri(
        relativePath: String, dir: Uri
    ): Uri?

    @Throws(IOException::class)
    fun createFile(
        dir: Uri, fileName: String, replace: Boolean
    ): Uri?

    @Throws(IOException::class)
    fun createFile(
        relativePath: String, dir: Uri, replace: Boolean
    ): Uri?

    fun getDirAvailableBytes(dir: Uri): Long
    fun getExtension(fileName: String?): String?
    fun getNameWithoutExtension(fileName: String?): String?
    fun isValidFatFilename(name: String?): Boolean
    fun buildValidFatFilename(name: String?): String?
    fun getDirName(dir: Uri): String?
    fun getFileSize(filePath: Uri): Long

    @Throws(IOException::class)
    fun truncate(filePath: Uri, newSize: Long)
    fun takePermissions(path: Uri)
    fun getDirPath(dir: Uri): String?
    fun exists(filePath: Uri): Boolean
    fun mkdirs(dir: Uri, relativePath: String): Boolean

    @Throws(IOException::class)
    fun createTmpFile(suffix: String?): File?

    /*
     * Copies the content of a InputStream into an OutputStream.
     * Uses a default buffer size of 8024 bytes.
     */
    @Throws(IOException::class)
    fun copy(input: InputStream?, output: OutputStream?): Long

    /*
     * Copies the content of a InputStream into an OutputStream
     */
    @Throws(IOException::class)
    fun copy(input: InputStream?, output: OutputStream?, buffersize: Int): Long
}
