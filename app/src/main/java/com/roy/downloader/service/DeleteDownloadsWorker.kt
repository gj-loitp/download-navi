package com.roy.downloader.service

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.roy.downloader.core.RepositoryHelper.getDataRepository
import com.roy.downloader.core.model.DownloadEngine
import java.util.UUID

/*
 * Used only by DownloadEngine.
 */
class DeleteDownloadsWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val context = applicationContext
        val engine = DownloadEngine.getInstance(context)
        val repo = getDataRepository(context)
        val data = inputData
        val idList = data.getStringArray(TAG_ID_LIST)
        val withFile = data.getBoolean(TAG_WITH_FILE, false)
        if (idList == null) return Result.failure()
        for (id in idList) {
            if (id == null) continue
            val uuid: UUID? = try {
                UUID.fromString(id)
            } catch (e: IllegalArgumentException) {
                continue
            }
            assert(repo != null)
            val info = repo?.getInfoById(uuid) ?: continue
            try {
                engine.doDeleteDownload(info, withFile)
            } catch (e: Exception) {
                Log.e(TAG, Log.getStackTraceString(e))
            }
        }
        return Result.success()
    }

    companion object {
        private val TAG = DeleteDownloadsWorker::class.java.simpleName
        const val TAG_ID_LIST = "id_list"
        const val TAG_WITH_FILE = "with_file"
    }
}
