package com.roy.downloader.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.roy.downloader.core.model.DownloadEngine;

/*
 * The receiver for Wi-Fi connection state changes and roaming state.
 */

public class ConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            DownloadEngine.getInstance(context).rescheduleDownloads();
        }
    }

    public static IntentFilter getFilter() {
        return new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }
}
