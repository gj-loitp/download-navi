package com.roy.downloader.core.storage;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

class DatabaseMigration {
    static Migration[] getMigrations() {
        return new Migration[]{
                MIGRATION_1_2,
                MIGRATION_2_3,
                MIGRATION_3_4,
                MIGRATION_4_5,
                MIGRATION_5_6,
        };
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            /* Add `numFailed` column to `DownloadInfo` table */
            database.execSQL("ALTER TABLE `DownloadInfo` ADD COLUMN `numFailed` INTEGER NOT NULL DEFAULT 0");

            /* Remove `numFailed` column from `DownloadPiece` table */
            database.execSQL("ALTER TABLE `DownloadPiece` RENAME TO `DownloadPiece_old`;");
            database.execSQL("DROP INDEX IF EXISTS `index_DownloadPiece_infoId`");

            database.execSQL("CREATE TABLE IF NOT EXISTS `DownloadPiece` (`pieceIndex` INTEGER NOT NULL, `infoId` TEXT NOT NULL, `size` INTEGER NOT NULL, `curBytes` INTEGER NOT NULL, `statusCode` INTEGER NOT NULL, `statusMsg` TEXT, `speed` INTEGER NOT NULL, PRIMARY KEY(`pieceIndex`, `infoId`), FOREIGN KEY(`infoId`) REFERENCES `DownloadInfo`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE );");
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_DownloadPiece_infoId` ON `DownloadPiece` (`infoId`)");

            database.execSQL("INSERT INTO `DownloadPiece` (`pieceIndex`, `infoId`, `size`, `curBytes`, `statusCode`, `statusMsg`, `speed`) SELECT `pieceIndex`, `infoId`, `size`, `curBytes`, `statusCode`, `statusMsg`, `speed` FROM `DownloadPiece_old`;");
            database.execSQL("DROP TABLE `DownloadPiece_old`;");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `DownloadInfo` ADD COLUMN `retryAfter` INTEGER NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE `DownloadInfo` ADD COLUMN `lastModify` INTEGER NOT NULL DEFAULT 0");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `DownloadInfo` ADD COLUMN `checksum` TEXT");
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `BrowserBookmark` (`url` TEXT NOT NULL, `name` TEXT NOT NULL, `dateAdded` INTEGER NOT NULL, PRIMARY KEY(`url`))");
        }
    };

    static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `DownloadInfo` ADD COLUMN `uncompressArchive` INTEGER NOT NULL DEFAULT 0");
        }
    };
}
