package com.roy.downloader.core.storage;

import androidx.lifecycle.LiveData;

import com.roy.downloader.core.model.data.entity.DownloadInfo;
import com.roy.downloader.core.model.data.entity.DownloadPiece;
import com.roy.downloader.core.model.data.entity.Header;
import com.roy.downloader.core.model.data.entity.InfoAndPieces;
import com.roy.downloader.core.model.data.entity.UserAgent;

import java.util.List;
import java.util.UUID;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface DataRepository {
    void addInfo(DownloadInfo info, List<Header> headers);

    void replaceInfoByUrl(DownloadInfo info, List<Header> headers);

    void updateInfo(DownloadInfo info,
                    boolean filePathChanged,
                    boolean rebuildPieces);

    void deleteInfo(DownloadInfo info, boolean withFile);

    Flowable<List<InfoAndPieces>> observeAllInfoAndPieces();

    Flowable<InfoAndPieces> observeInfoAndPiecesById(UUID id);

    Single<List<InfoAndPieces>> getAllInfoAndPiecesSingle();

    List<DownloadInfo> getAllInfo();

    DownloadInfo getInfoById(UUID id);

    Single<DownloadInfo> getInfoByIdSingle(UUID id);

    int updatePiece(DownloadPiece piece);

    List<DownloadPiece> getPiecesById(UUID infoId);

    List<DownloadPiece> getPiecesByIdSorted(UUID infoId);

    DownloadPiece getPiece(int index, UUID infoId);

    List<Header> getHeadersById(UUID infoId);

    void addHeader(Header header);

    void addUserAgent(UserAgent agent);

    void deleteUserAgent(UserAgent agent);

    LiveData<List<UserAgent>> observeUserAgents();
}
