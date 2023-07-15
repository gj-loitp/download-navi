package com.roy.downloader.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.roy.downloader.core.model.DownloadEngine

/*
 * The receiver for Wi-Fi connection state changes and roaming state.
 */
class ConnectionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action != null && action == ConnectivityManager.CONNECTIVITY_ACTION) {
            DownloadEngine.getInstance(context).rescheduleDownloads()
        }
    }

    companion object {
        @JvmStatic
        val filter: IntentFilter
            get() = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    }
}
