package com.roy.downloader.core.model.data

import java.util.UUID

/*
 * Provides information about the download thread status after stopping.
 */
class DownloadResult(
    var infoId: UUID,
    var status: Status
) {
    enum class Status {
        FINISHED, PAUSED, STOPPED
    }
}
