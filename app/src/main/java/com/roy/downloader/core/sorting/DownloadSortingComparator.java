package com.roy.downloader.core.sorting;

import androidx.annotation.NonNull;

import com.roy.downloader.ui.main.DownloadItem;

import java.util.Comparator;

public class DownloadSortingComparator implements Comparator<DownloadItem> {
    private final DownloadSorting sorting;

    public DownloadSortingComparator(@NonNull DownloadSorting sorting) {
        this.sorting = sorting;
    }

    public DownloadSorting getSorting() {
        return sorting;
    }

    @Override
    public int compare(DownloadItem o1, DownloadItem o2) {
        return DownloadSorting.SortingColumns.fromValue(sorting.getColumnName())
                .compare(o1, o2, sorting.getDirection());
    }
}
