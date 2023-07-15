package com.roy.downloader.core.exception

/*
* Not enough free space exception.
*/
class FreeSpaceException : Exception {
    constructor()
    constructor(message: String?) : super(message)
    constructor(e: Exception) : super(e.message) {
        super.setStackTrace(e.stackTrace)
    }
}
