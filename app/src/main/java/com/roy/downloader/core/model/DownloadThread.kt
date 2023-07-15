package com.roy.downloader.core.model

import com.roy.downloader.core.model.data.DownloadResult
import java.util.concurrent.Callable

internal interface DownloadThread : Callable<DownloadResult?> {
    fun requestStop()
    fun requestPause()
    val isRunning: Boolean
}
