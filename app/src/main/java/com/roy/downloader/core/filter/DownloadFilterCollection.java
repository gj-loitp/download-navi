package com.roy.downloader.core.filter;

import androidx.annotation.NonNull;

import com.roy.downloader.core.model.data.StatusCode;
import com.roy.downloader.core.utils.DateUtils;
import com.roy.downloader.core.utils.MimeTypeUtils;

public class DownloadFilterCollection {
    public static DownloadFilter all() {
        return (infoAndPieces) -> true;
    }

    public static DownloadFilter category(@NonNull MimeTypeUtils.Category category) {
        return (infoAndPieces) -> MimeTypeUtils.getCategory(infoAndPieces.info.mimeType).equals(category);
    }

    public static DownloadFilter statusStopped() {
        return (infoAndPieces) -> StatusCode.isStatusStoppedOrPaused(infoAndPieces.info.statusCode);
    }

    public static DownloadFilter statusRunning() {
        return (infoAndPieces) ->
                infoAndPieces.info.statusCode == StatusCode.STATUS_RUNNING ||
                        infoAndPieces.info.statusCode == StatusCode.STATUS_FETCH_METADATA;
    }

    public static DownloadFilter dateAddedToday() {
        return (infoAndPieces) -> {
            long dateAdded = infoAndPieces.info.dateAdded;
            long timeMillis = System.currentTimeMillis();

            return dateAdded >= DateUtils.startOfToday(timeMillis) &&
                    dateAdded <= DateUtils.endOfToday(timeMillis);
        };
    }

    public static DownloadFilter dateAddedYesterday() {
        return (infoAndPieces) -> {
            long dateAdded = infoAndPieces.info.dateAdded;
            long timeMillis = System.currentTimeMillis();

            return dateAdded >= DateUtils.startOfYesterday(timeMillis) &&
                    dateAdded <= DateUtils.endOfYesterday(timeMillis);
        };
    }

    public static DownloadFilter dateAddedWeek() {
        return (infoAndPieces) -> {
            long dateAdded = infoAndPieces.info.dateAdded;
            long timeMillis = System.currentTimeMillis();

            return dateAdded >= DateUtils.startOfWeek(timeMillis) &&
                    dateAdded <= DateUtils.endOfWeek(timeMillis);
        };
    }

    public static DownloadFilter dateAddedMonth() {
        return (infoAndPieces) -> {
            long dateAdded = infoAndPieces.info.dateAdded;
            long timeMillis = System.currentTimeMillis();

            return dateAdded >= DateUtils.startOfMonth(timeMillis) &&
                    dateAdded <= DateUtils.endOfMonth(timeMillis);
        };
    }

    public static DownloadFilter dateAddedYear() {
        return (infoAndPieces) -> {
            long dateAdded = infoAndPieces.info.dateAdded;
            long timeMillis = System.currentTimeMillis();

            return dateAdded >= DateUtils.startOfYear(timeMillis) &&
                    dateAdded <= DateUtils.endOfYear(timeMillis);
        };
    }
}
