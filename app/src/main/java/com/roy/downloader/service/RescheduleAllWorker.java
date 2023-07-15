package com.roy.downloader.service;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;
import com.roy.downloader.core.RepositoryHelper;
import com.roy.downloader.core.model.DownloadScheduler;
import com.roy.downloader.core.model.data.entity.DownloadInfo;
import com.roy.downloader.core.storage.DataRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/*
 * Reschedule all RunDownloadWorker's. Used only by DownloadScheduler.
 */

public class RescheduleAllWorker extends Worker {
    public RescheduleAllWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        DataRepository repo = RepositoryHelper.getDataRepository(context);

        ListenableFuture<List<WorkInfo>> future = WorkManager.getInstance(context)
                .getWorkInfosByTag(DownloadScheduler.TAG_WORK_RUN_TYPE);
        try {
            for (WorkInfo workInfo : future.get()) {
                if (workInfo.getState().isFinished())
                    continue;

                String runTag = null;
                for (String tag : workInfo.getTags()) {
                    if (!tag.equals(DownloadScheduler.TAG_WORK_RUN_TYPE) &&
                            tag.startsWith(DownloadScheduler.TAG_WORK_RUN_TYPE)) {
                        runTag = tag;
                        /* Get the first tag because it's unique */
                        break;
                    }
                }
                String downloadId = (runTag == null ? null : DownloadScheduler.extractDownloadIdFromTag(runTag));
                if (downloadId == null)
                    continue;

                DownloadInfo info;
                try {
                    info = repo.getInfoById(UUID.fromString(downloadId));

                } catch (Exception e) {
                    continue;
                }
                if (info == null)
                    continue;

                DownloadScheduler.run(context, info);
            }
        } catch (InterruptedException | ExecutionException e) {
            /* Ignore */
        }

        return Result.success();
    }
}
