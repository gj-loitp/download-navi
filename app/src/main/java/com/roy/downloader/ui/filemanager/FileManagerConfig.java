package com.roy.downloader.ui.filemanager;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/*
 * Specifies the start folder and choose mode (folder or file choose). Part of FileManagerDialog.
 */

public class FileManagerConfig implements Parcelable {
    public static final int FILE_CHOOSER_MODE = 0;
    public static final int DIR_CHOOSER_MODE = 1;
    public static final int SAVE_FILE_MODE = 2;

    public String path;
    public String title;
    /* File extension, e.g 'torrent' */
    public List<String> highlightFileTypes;
    /* For save dialog */
    public String fileName;
    public int showMode;
    public boolean canReplace;
    public String mimeType;

    public FileManagerConfig(String path, String title, int mode) {
        this.path = path;
        this.title = title;
        showMode = mode;
    }

    public FileManagerConfig(Parcel source) {
        path = source.readString();
        title = source.readString();
        highlightFileTypes = new ArrayList<>();
        source.readStringList(highlightFileTypes);
        showMode = source.readInt();
        fileName = source.readString();
        canReplace = source.readByte() != 0;
        mimeType = source.readString();
    }

    public FileManagerConfig setFileName(String name) {
        fileName = name;

        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(title);
        dest.writeStringList(highlightFileTypes);
        dest.writeInt(showMode);
        dest.writeString(fileName);
        dest.writeByte((byte) (canReplace ? 1 : 0));
        dest.writeString(mimeType);
    }

    public static final Creator<FileManagerConfig> CREATOR =
            new Creator<>() {
                @Override
                public FileManagerConfig createFromParcel(Parcel source) {
                    return new FileManagerConfig(source);
                }

                @Override
                public FileManagerConfig[] newArray(int size) {
                    return new FileManagerConfig[size];
                }
            };
}
