package com.roy.downloader.core.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
public class BrowserBookmark implements Parcelable {
    @PrimaryKey
    @NonNull
    public String url;
    @NonNull
    public String name;
    public long dateAdded;

    public BrowserBookmark(@NonNull String url, @NonNull String name, long dateAdded) {
        this.url = url;
        this.name = name;
        this.dateAdded = dateAdded;
    }

    @Ignore
    public BrowserBookmark(@NonNull BrowserBookmark bookmark) {
        this.url = bookmark.url;
        this.name = bookmark.name;
        this.dateAdded = bookmark.dateAdded;
    }

    @Ignore
    public BrowserBookmark(Parcel source) {
        url = Objects.requireNonNull(source.readString());
        name = Objects.requireNonNull(source.readString());
        dateAdded = source.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
        dest.writeLong(dateAdded);
    }

    public static final Creator<BrowserBookmark> CREATOR = new Creator<>() {
        @Override
        public BrowserBookmark createFromParcel(Parcel source) {
            return new BrowserBookmark(source);
        }

        @Override
        public BrowserBookmark[] newArray(int size) {
            return new BrowserBookmark[size];
        }
    };

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrowserBookmark that = (BrowserBookmark) o;

        return url.equals(that.url);
    }

    @NonNull
    @Override
    public String toString() {
        return "BrowserBookmark{" + "url='" + url + '\'' + ", name='" + name + '\'' + ", dateAdded=" + SimpleDateFormat.getDateTimeInstance().format(new Date(dateAdded)) + '}';
    }
}
