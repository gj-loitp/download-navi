package com.roy.downloader.ui.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.roy.downloader.R;
import com.roy.downloader.core.model.data.StatusCode;
import com.roy.downloader.core.model.data.entity.DownloadInfo;
import com.roy.downloader.core.utils.Utils;
import com.roy.downloader.ui.BaseAlertDialog;
import com.roy.downloader.ui.adddownload.ActivityAddDownload;
import com.roy.downloader.ui.adddownload.AddInitParams;

import java.util.Collections;

import io.reactivex.disposables.Disposable;

public class FinishedFragmentDownloads extends FragmentDownloads implements DownloadListAdapter.FinishClickListener, DownloadListAdapter.ErrorClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = FinishedFragmentDownloads.class.getSimpleName();

    private static final String TAG_DELETE_DOWNLOAD_DIALOG = "delete_download_dialog";
    private static final String TAG_DOWNLOAD_FOR_DELETION = "download_for_deletion";

    private BaseAlertDialog deleteDownloadDialog;
    private BaseAlertDialog.SharedViewModel dialogViewModel;
    private DownloadInfo downloadForDeletion;

    public static FinishedFragmentDownloads newInstance() {
        FinishedFragmentDownloads fragment = new FinishedFragmentDownloads();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public FinishedFragmentDownloads() {
        super((item) -> StatusCode.isStatusCompleted(item.info.statusCode));
    }

    @Override
    public void onStart() {
        super.onStart();

        subscribeAdapter();
        subscribeAlertDialog();
    }

    private void subscribeAlertDialog() {
        Disposable d = dialogViewModel.observeEvents().subscribe((event) -> {
            if (event.dialogTag == null || !event.dialogTag.equals(TAG_DELETE_DOWNLOAD_DIALOG) || deleteDownloadDialog == null)
                return;
            switch (event.type) {
                case POSITIVE_BUTTON_CLICKED:
                    Dialog dialog = deleteDownloadDialog.getDialog();
                    if (dialog != null && downloadForDeletion != null) {
                        CheckBox withFile = dialog.findViewById(R.id.deleteWithFile);
                        deleteDownload(downloadForDeletion, withFile.isChecked());
                    }
                case NEGATIVE_BUTTON_CLICKED:
                    downloadForDeletion = null;
                    deleteDownloadDialog.dismiss();
                    break;
            }
        });
        disposables.add(d);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null)
            downloadForDeletion = savedInstanceState.getParcelable(TAG_DOWNLOAD_FOR_DELETION);

        FragmentManager fm = getChildFragmentManager();
        deleteDownloadDialog = (BaseAlertDialog) fm.findFragmentByTag(TAG_DELETE_DOWNLOAD_DIALOG);
        dialogViewModel = new ViewModelProvider(activity).get(BaseAlertDialog.SharedViewModel.class);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(TAG_DOWNLOAD_FOR_DELETION, downloadForDeletion);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClicked(@NonNull DownloadItem item) {
        Intent file = Utils.createOpenFileIntent(activity.getApplicationContext(), item.info);
        if (file != null) {
            startActivity(Intent.createChooser(file, getString(R.string.open_using)));
        } else {
            Toast.makeText(activity.getApplicationContext(), getString(R.string.file_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemMenuClicked(int menuId, @NonNull DownloadItem item) {
        switch (menuId) {
            case R.id.deleteMenu -> {
                downloadForDeletion = item.info;
                showDeleteDownloadDialog();
            }
            case R.id.openDetailsMenu -> showDetailsDialog(item.info.id);
            case R.id.shareMenu -> shareDownload(item);
            case R.id.shareUrlMenu -> shareUrl(item);
            case R.id.reDownloadMenu -> showAddDownloadDialog(item.info);
        }
    }

    @Override
    public void onItemResumeClicked(@NonNull DownloadItem item) {
        viewModel.resumeIfError(item.info);
    }

    private void showDeleteDownloadDialog() {
        if (!isAdded()) return;

        FragmentManager fm = getChildFragmentManager();
        if (fm.findFragmentByTag(TAG_DELETE_DOWNLOAD_DIALOG) == null) {
            deleteDownloadDialog = BaseAlertDialog.newInstance(getString(R.string.deleting), getString(R.string.delete_selected_download), R.layout.dlg_dialog_delete_downloads, getString(R.string.ok), getString(R.string.cancel), null, false);

            deleteDownloadDialog.show(fm, TAG_DELETE_DOWNLOAD_DIALOG);
        }
    }

    private void deleteDownload(DownloadInfo info, boolean withFile) {
        viewModel.deleteDownload(info, withFile);
    }

    private void shareDownload(DownloadItem item) {
        Intent intent = Utils.makeFileShareIntent(activity.getApplicationContext(), Collections.singletonList(item));
        if (intent != null) {
            startActivity(Intent.createChooser(intent, getString(R.string.share_via)));
        } else {
            Toast.makeText(activity.getApplicationContext(), getResources().getQuantityString(R.plurals.unable_sharing, 1), Toast.LENGTH_SHORT).show();
        }
    }

    private void shareUrl(DownloadItem item) {
        startActivity(Intent.createChooser(Utils.makeShareUrlIntent(item.info.url), getString(R.string.share_via)));
    }

    private void showAddDownloadDialog(DownloadInfo info) {
        AddInitParams initParams = new AddInitParams();
        initParams.url = info.url;
        initParams.dirPath = info.dirPath;
        initParams.fileName = info.fileName;
        initParams.description = info.description;
        initParams.userAgent = info.userAgent;
        initParams.unmeteredConnectionsOnly = info.unmeteredConnectionsOnly;
        initParams.retry = info.retry;
        initParams.replaceFile = true;

        Intent i = new Intent(activity, ActivityAddDownload.class);
        i.putExtra(ActivityAddDownload.TAG_INIT_PARAMS, initParams);
        startActivity(i);
    }
}
