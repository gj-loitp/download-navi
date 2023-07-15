package com.roy.downloader.ui.adddownload;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AddInitParams implements Parcelable {
    public String url;
    public String fileName;
    public String description;
    public String userAgent;
    public Uri dirPath;
    @Nullable
    public Boolean unmeteredConnectionsOnly;
    @Nullable
    public Boolean retry;
    @Nullable
    public Boolean replaceFile;
    @Nullable
    public Integer numPieces;
    @Nullable
    public Boolean uncompressArchive;

    public AddInitParams() {
    }

    public AddInitParams(@NonNull Parcel source) {
        dirPath = source.readParcelable(Uri.class.getClassLoader());
        url = source.readString();
        fileName = source.readString();
        description = source.readString();
        userAgent = source.readString();
        byte unmeteredConnectionsOnlyVal = source.readByte();
        if (unmeteredConnectionsOnlyVal != -1) {
            unmeteredConnectionsOnly = unmeteredConnectionsOnlyVal > 0;
        }
        byte retryVal = source.readByte();
        if (retryVal != -1) {
            retry = retryVal > 0;
        }
        byte replaceFileVal = source.readByte();
        if (replaceFileVal != -1) {
            replaceFile = replaceFileVal > 0;
        }
        int numPiecesVal = source.readInt();
        if (numPiecesVal != -1) {
            numPieces = numPiecesVal;
        }
        int uncompressArchiveVal = source.readInt();
        if (uncompressArchiveVal != -1) {
            uncompressArchive = uncompressArchiveVal > 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(dirPath, flags);
        dest.writeString(url);
        dest.writeString(fileName);
        dest.writeString(description);
        dest.writeString(userAgent);
        if (unmeteredConnectionsOnly == null) {
            dest.writeByte((byte) -1);
        } else {
            dest.writeByte((byte) (unmeteredConnectionsOnly ? 1 : 0));
        }
        if (retry == null) {
            dest.writeByte((byte) -1);
        } else {
            dest.writeByte((byte) (retry ? 1 : 0));
        }
        if (replaceFile == null) {
            dest.writeByte((byte) -1);
        } else {
            dest.writeByte((byte) (replaceFile ? 1 : 0));
        }
        if (numPieces == null) {
            dest.writeInt(-1);
        } else {
            dest.writeInt(numPieces);
        }
        if (uncompressArchive == null) {
            dest.writeByte((byte) -1);
        } else {
            dest.writeByte((byte) (uncompressArchive ? 1 : 0));
        }
    }

    public static final Parcelable.Creator<AddInitParams> CREATOR =
            new Parcelable.Creator<AddInitParams>() {
                @Override
                public AddInitParams createFromParcel(Parcel source) {
                    return new AddInitParams(source);
                }

                @Override
                public AddInitParams[] newArray(int size) {
                    return new AddInitParams[size];
                }
            };

    @Override
    public String toString() {
        return "AddInitParams{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", description='" + description + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", dirPath=" + dirPath +
                ", unmeteredConnectionsOnly=" + unmeteredConnectionsOnly +
                ", retry=" + retry +
                ", replaceFile=" + replaceFile +
                ", numPieces=" + numPieces +
                ", uncompressArchive=" + uncompressArchive +
                '}';
    }
}
