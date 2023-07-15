package com.roy.downloader.core.sorting;

import com.roy.downloader.ui.main.DownloadItem;

import java.util.Objects;

public class DownloadSorting extends BaseSorting {
    public enum SortingColumns implements SortingColumnsInterface<DownloadItem> {
        none {
            @Override
            public int compare(DownloadItem item1, DownloadItem item2,
                               Direction direction) {
                return 0;
            }
        },
        name {
            @Override
            public int compare(DownloadItem item1, DownloadItem item2,
                               Direction direction) {
                if (direction == Direction.ASC)
                    return item1.info.fileName.compareTo(item2.info.fileName);
                else
                    return item2.info.fileName.compareTo(item1.info.fileName);
            }
        },
        size {
            @Override
            public int compare(DownloadItem item1, DownloadItem item2,
                               Direction direction) {
                if (direction == Direction.ASC)
                    return Long.compare(item1.info.totalBytes, item2.info.totalBytes);
                else
                    return Long.compare(item2.info.totalBytes, item1.info.totalBytes);
            }
        },
        dateAdded {
            @Override
            public int compare(DownloadItem item1, DownloadItem item2,
                               Direction direction) {
                if (direction == Direction.ASC)
                    return Long.compare(item1.info.dateAdded, item2.info.dateAdded);
                else
                    return Long.compare(item2.info.dateAdded, item1.info.dateAdded);
            }
        },
        category {
            @Override
            public int compare(DownloadItem item1, DownloadItem item2,
                               Direction direction) {
                if (direction == Direction.ASC)
                    return item1.info.mimeType.compareTo(item2.info.mimeType);
                else
                    return item2.info.mimeType.compareTo(item1.info.mimeType);
            }
        };

        public static SortingColumns fromValue(String value) {
            for (SortingColumns column : Objects.requireNonNull(SortingColumns.class.getEnumConstants()))
                if (column.toString().equalsIgnoreCase(value))
                    return column;

            return SortingColumns.none;
        }
    }

    public DownloadSorting(SortingColumns columnName, Direction direction) {
        super(columnName.name(), direction);
    }
}
