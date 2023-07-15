package com.roy.downloader.core.model

import java.util.UUID

abstract class DownloadEngineListener {
    open fun onDownloadsCompleted() {}
    open fun onApplyingParams(id: UUID) {}
    open fun onParamsApplied(id: UUID, name: String?, e: Throwable?) {}
}
