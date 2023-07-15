package com.roy.downloader.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.roy.downloader.core.model.data.StatusCode;

public class QueuedFragmentDownloads extends FragmentDownloads implements DownloadListAdapter.QueueClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = QueuedFragmentDownloads.class.getSimpleName();

    public static QueuedFragmentDownloads newInstance() {
        QueuedFragmentDownloads fragment = new QueuedFragmentDownloads();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public QueuedFragmentDownloads() {
        super((item) -> !StatusCode.isStatusCompleted(item.info.statusCode));
    }

    @Override
    public void onStart() {
        super.onStart();

        subscribeAdapter();
    }

    @Override
    public void onItemClicked(@NonNull DownloadItem item) {
        showDetailsDialog(item.info.id);
    }

    @Override
    public void onItemPauseClicked(@NonNull DownloadItem item) {
        viewModel.pauseResumeDownload(item.info);
    }

    @Override
    public void onItemCancelClicked(@NonNull DownloadItem item) {
        viewModel.deleteDownload(item.info, true);
    }
}
