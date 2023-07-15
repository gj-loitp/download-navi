package com.roy.downloader.core.model.data;

import java.util.UUID;

/*
 * Provides information about the download thread status after stopping.
 */

public class DownloadResult {
    public enum Status {
        FINISHED, PAUSED, STOPPED
    }

    public UUID infoId;
    public Status status;

    public DownloadResult(UUID infoId, Status status) {
        this.infoId = infoId;
        this.status = status;
    }
}
