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

public class RunAllWorker extends Worker {
    @SuppressWarnings("unused")
    private static final String TAG = RunAllWorker.class.getSimpleName();

    public static final String TAG_IGNORE_PAUSED = "ignore_paused";

    public RunAllWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        DataRepository repo = RepositoryHelper.getDataRepository(context);
        boolean ignorePaused = getInputData().getBoolean(TAG_IGNORE_PAUSED, false);

        assert repo != null;
        List<DownloadInfo> infoList = repo.getAllInfo();
        if (infoList.isEmpty())
            return Result.success();

        for (DownloadInfo info : infoList) {
            if (info == null)
                continue;

            if (info.statusCode == StatusCode.STATUS_STOPPED ||
                    (!ignorePaused && info.statusCode == StatusCode.STATUS_PAUSED))
                DownloadScheduler.run(context, info);
        }

        return Result.success();
    }
}
