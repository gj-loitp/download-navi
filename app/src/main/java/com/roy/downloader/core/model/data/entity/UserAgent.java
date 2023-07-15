package com.roy.downloader.core.model.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserAgent {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @NonNull
    public String userAgent;
    /* Makes it impossible to delete or change user agent */
    public boolean readOnly = false;

    public UserAgent(@NonNull String userAgent) {
        this.userAgent = userAgent;
    }
}
