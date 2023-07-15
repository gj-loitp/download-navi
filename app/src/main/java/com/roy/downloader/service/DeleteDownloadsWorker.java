package com.roy.downloader.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.roy.downloader.core.RepositoryHelper;
import com.roy.downloader.core.model.DownloadEngine;
import com.roy.downloader.core.model.data.entity.DownloadInfo;
import com.roy.downloader.core.storage.DataRepository;

import java.util.UUID;

/*
 * Used only by DownloadEngine.
 */

public class DeleteDownloadsWorker extends Worker {
    @SuppressWarnings("unused")
    private static final String TAG = DeleteDownloadsWorker.class.getSimpleName();

    public static final String TAG_ID_LIST = "id_list";
    public static final String TAG_WITH_FILE = "with_file";

    public DeleteDownloadsWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        DownloadEngine engine = DownloadEngine.getInstance(context);
        DataRepository repo = RepositoryHelper.getDataRepository(context);

        Data data = getInputData();
        String[] idList = data.getStringArray(TAG_ID_LIST);
        boolean withFile = data.getBoolean(TAG_WITH_FILE, false);
        if (idList == null)
            return Result.failure();

        for (String id : idList) {
            if (id == null)
                continue;
            UUID uuid;
            try {
                uuid = UUID.fromString(id);

            } catch (IllegalArgumentException e) {
                continue;
            }

            assert repo != null;
            DownloadInfo info = repo.getInfoById(uuid);
            if (info == null)
                continue;
            try {
                engine.doDeleteDownload(info, withFile);

            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }

        return Result.success();
    }
}
