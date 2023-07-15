package com.roy.downloader.core.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.util.List;

public class InfoAndPieces implements Parcelable {
    @Embedded
    public DownloadInfo info;
    @Relation(parentColumn = "id", entityColumn = "infoId")
    public List<DownloadPiece> pieces;

    /*
     * Do not use, only for DAO
     */
    public InfoAndPieces() {
    }

    @Ignore
    public InfoAndPieces(Parcel source) {
        info = source.readParcelable(DownloadInfo.class.getClassLoader());
        pieces = source.createTypedArrayList(DownloadPiece.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(info, flags);
        dest.writeTypedList(pieces);
    }

    public static final Creator<InfoAndPieces> CREATOR =
            new Creator<InfoAndPieces>() {
                @Override
                public InfoAndPieces createFromParcel(Parcel source) {
                    return new InfoAndPieces(source);
                }

                @Override
                public InfoAndPieces[] newArray(int size) {
                    return new InfoAndPieces[size];
                }
            };

    @Override
    public int hashCode() {
        return info.id.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (!(o instanceof InfoAndPieces infoAndPieces))
            return false;

        if (o == this)
            return true;

        if (pieces.size() != infoAndPieces.pieces.size())
            return false;

        return info.equals(infoAndPieces.info) &&
                pieces.containsAll(infoAndPieces.pieces);
    }

    @NonNull
    @Override
    public String toString() {
        return "InfoAndPieces{" +
                "info=" + info +
                ", pieces=" + pieces +
                '}';
    }
}
