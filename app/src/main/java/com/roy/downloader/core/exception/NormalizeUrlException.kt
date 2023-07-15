package com.roy.downloader.core.exception

class NormalizeUrlException : Exception {
    constructor(message: String?, e: Exception?) : super(message) {
        initCause(e)
    }

    constructor(message: String?) : super(message)
}
