package com.roy.downloader.core.model.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "download_info_headers",
        indices = {@Index(value = "infoId")},
        foreignKeys = @ForeignKey(
                entity = DownloadInfo.class,
                parentColumns = "id",
                childColumns = "infoId",
                onDelete = CASCADE))
public class Header {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @NonNull
    public UUID infoId;
    public String name;
    public String value;

    public Header(@NonNull UUID infoId, String name, String value) {
        this.infoId = infoId;
        this.name = name;
        this.value = value;
    }
}
