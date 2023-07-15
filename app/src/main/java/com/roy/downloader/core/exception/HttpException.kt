package com.roy.downloader.core.exception

class HttpException @JvmOverloads constructor(
    message: String?,
    val responseCode: Int = 0
) :
    Exception(message) {

    constructor(
        message: String?,
        responseCode: Int,
        e: Exception?
    ) : this(message, responseCode) {
        initCause(e)
    }
}
