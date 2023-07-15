package com.roy.downloader.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.roy.downloader.core.RepositoryHelper;
import com.roy.downloader.core.model.DownloadScheduler;
import com.roy.downloader.core.model.data.StatusCode;
import com.roy.downloader.core.model.data.entity.DownloadInfo;
import com.roy.downloader.core.storage.DataRepository;

import java.util.List;

/*
 * Used only by DownloadScheduler.
 */

public class RestoreDownloadsWorker extends Worker {
    @SuppressWarnings("unused")
    private static final String TAG = RestoreDownloadsWorker.class.getSimpleName();

    public RestoreDownloadsWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        DataRepository repo = RepositoryHelper.getDataRepository(context);

        List<DownloadInfo> infoList = repo.getAllInfo();
        if (infoList.isEmpty())
            return Result.success();

        for (DownloadInfo info : infoList) {
            if (info == null)
                continue;
            /*
             * Also restore those downloads that are incorrectly completed and
             * have the wrong status (for example, after crashing)
             */
            if (info.statusCode == StatusCode.STATUS_PENDING ||
                    info.statusCode == StatusCode.STATUS_RUNNING ||
                    info.statusCode == StatusCode.STATUS_FETCH_METADATA)
                DownloadScheduler.run(context, info);
        }

        return Result.success();
    }
}
