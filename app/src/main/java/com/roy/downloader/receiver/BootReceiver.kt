package com.roy.downloader.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.roy.downloader.core.RepositoryHelper;
import com.roy.downloader.core.model.DownloadEngine;
import com.roy.downloader.core.settings.SettingsRepository;

/*
 * The receiver for autostart stopped downloads.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == null)
            return;

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            SettingsRepository pref = RepositoryHelper.getSettingsRepository(context.getApplicationContext());
            if (pref != null && pref.autostart()) {
                DownloadEngine engine = DownloadEngine.getInstance(context.getApplicationContext());
                engine.restoreDownloads();
            }
        }
    }
}
