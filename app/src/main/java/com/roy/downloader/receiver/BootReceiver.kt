package com.roy.downloader.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.roy.downloader.core.RepositoryHelper.getSettingsRepository
import com.roy.downloader.core.model.DownloadEngine

/*
 * The receiver for autostart stopped downloads.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == null) return
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val pref = getSettingsRepository(context.applicationContext)
            if (pref != null && pref.autostart()) {
                val engine = DownloadEngine.getInstance(context.applicationContext)
                engine.restoreDownloads()
            }
        }
    }
}
