package com.roy.downloader.core.exception

class FileAlreadyExistsException : Exception {
    constructor()
    constructor(message: String?) : super(message)
    constructor(e: Exception) : super(e.message) {
        super.setStackTrace(e.stackTrace)
    }
}
