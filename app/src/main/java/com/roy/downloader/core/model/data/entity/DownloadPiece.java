package com.roy.downloader.core.model.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.roy.downloader.core.model.data.StatusCode;

import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;

/*
 * The class encapsulates information about piece of download.
 * A piece is a HTTP/S connection that downloads a certain part
 * of the data from the server (and currently stores it in a file position
 * specifically reserved for the piece).
 *
 * As a rule, the entire file size is divided equally between all pieces,
 * so the size of the parts is the same, but the last piece may have a larger size.
 * If the file size is unknown, only one download piece is created,
 * which has a negative size (-1)
 *
 * If the server doesn't support HTTP Range Request (TODO: support RANG for FTP),
 * then the entire file is downloaded in only one piece.
 */

@Entity(primaryKeys = {"pieceIndex", "infoId"},
        indices = {@Index(value = "infoId")},
        foreignKeys = @ForeignKey(
                entity = DownloadInfo.class,
                parentColumns = "id",
                childColumns = "infoId",
                onDelete = CASCADE))
public class DownloadPiece implements Parcelable {
    @ColumnInfo(name = "pieceIndex")
    public int index;
    @NonNull
    public UUID infoId;
    public long size;
    public long curBytes;
    public int statusCode = StatusCode.STATUS_PENDING;
    public String statusMsg;
    public long speed;

    public DownloadPiece(@NonNull UUID infoId, int index, long size, long curBytes) {
        this.infoId = infoId;
        this.index = index;
        this.size = size;
        this.curBytes = curBytes;
    }

    @Ignore
    public DownloadPiece(@NonNull Parcel source) {
        infoId = (UUID) source.readSerializable();
        size = source.readLong();
        index = source.readInt();
        curBytes = source.readLong();
        statusCode = source.readInt();
        statusMsg = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(infoId);
        dest.writeLong(size);
        dest.writeInt(index);
        dest.writeLong(curBytes);
        dest.writeInt(statusCode);
        dest.writeString(statusMsg);
    }

    public static final Creator<DownloadPiece> CREATOR =
            new Creator<>() {
                @Override
                public DownloadPiece createFromParcel(Parcel source) {
                    return new DownloadPiece(source);
                }

                @Override
                public DownloadPiece[] newArray(int size) {
                    return new DownloadPiece[size];
                }
            };

    @Override
    public int hashCode() {
        int prime = 31, result = 1;

        result = prime * result + index;
        result = prime * result + infoId.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DownloadPiece piece))
            return false;

        if (o == this)
            return true;

        return infoId.equals(piece.infoId) &&
                index == piece.index &&
                size == piece.size &&
                curBytes == piece.curBytes &&
                speed == piece.speed &&
                statusCode == piece.statusCode &&
                (statusMsg == null || statusMsg.equals(piece.statusMsg));
    }

    @NonNull
    @Override
    public String toString() {
        return "DownloadPiece{" +
                "index=" + index +
                ", infoId=" + infoId +
                ", size=" + size +
                ", curBytes=" + curBytes +
                ", statusCode=" + statusCode +
                ", statusMsg='" + statusMsg + '\'' +
                ", speed=" + speed +
                '}';
    }
}
