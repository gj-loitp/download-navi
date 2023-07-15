package com.roy.downloader.core.model;

import androidx.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.UUID;

/*
 * The priority queue if we want to defer download for an indefinite period of time,
 * for example, simultaneous downloads.
 */

class DownloadQueue {
    @SuppressWarnings("unused")
    private static final String TAG = DownloadQueue.class.getSimpleName();

    private final ArrayDeque<UUID> queue = new ArrayDeque<>();

    public void push(@NonNull UUID downloadId) {
        if (queue.contains(downloadId))
            return;
        queue.push(downloadId);
    }

    public UUID pop() {
        UUID downloadId = null;
        while (downloadId == null) {
            try {
                downloadId = queue.pop();

            } catch (NoSuchElementException e) {
                /* Queue is empty, return */
                return null;
            }
        }

        return downloadId;
    }
}
