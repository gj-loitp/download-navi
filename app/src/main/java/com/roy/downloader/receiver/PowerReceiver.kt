package com.roy.downloader.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.roy.downloader.core.model.DownloadEngine

/*
 * The receiver for power monitoring.
 */
class PowerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        when (action) {
            Intent.ACTION_BATTERY_LOW, Intent.ACTION_BATTERY_OKAY, Intent.ACTION_POWER_CONNECTED, Intent.ACTION_POWER_DISCONNECTED, Intent.ACTION_BATTERY_CHANGED -> DownloadEngine.getInstance(
                context
            ).rescheduleDownloads()
        }
    }

    companion object {
        /* About BATTERY_LOW and BATTERY_OKAY see https://code.google.com/p/android/issues/detail?id=36712 */
        @JvmStatic
        val filter: IntentFilter
            get() {
                val filter = IntentFilter()
                filter.addAction(Intent.ACTION_POWER_CONNECTED)
                filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
                /* About BATTERY_LOW and BATTERY_OKAY see https://code.google.com/p/android/issues/detail?id=36712 */
                filter.addAction(Intent.ACTION_BATTERY_LOW)
                filter.addAction(Intent.ACTION_BATTERY_OKAY)
                return filter
            }

        @JvmStatic
        val customFilter: IntentFilter
            get() {
                val filter = IntentFilter()
                filter.addAction(Intent.ACTION_POWER_CONNECTED)
                filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
                filter.addAction(Intent.ACTION_BATTERY_CHANGED)
                return filter
            }
    }
}
