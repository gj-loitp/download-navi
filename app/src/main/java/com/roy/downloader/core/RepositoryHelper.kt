package com.roy.downloader.core

import android.content.Context
import com.roy.downloader.core.settings.SettingsRepository
import com.roy.downloader.core.settings.SettingsRepositoryImpl
import com.roy.downloader.core.storage.AppDatabase
import com.roy.downloader.core.storage.BrowserRepository
import com.roy.downloader.core.storage.BrowserRepositoryImpl
import com.roy.downloader.core.storage.DataRepository
import com.roy.downloader.core.storage.DataRepositoryImpl

object RepositoryHelper {
    private var dataRepo: DataRepositoryImpl? = null
    private var settingsRepo: SettingsRepositoryImpl? = null
    private var browserRepository: BrowserRepository? = null

    @JvmStatic
    @Synchronized
    fun getDataRepository(appContext: Context): DataRepository? {
        if (dataRepo == null) dataRepo = DataRepositoryImpl(
            appContext,
            AppDatabase.getInstance(appContext)
        )
        return dataRepo
    }

    @JvmStatic
    @Synchronized
    fun getSettingsRepository(appContext: Context): SettingsRepository? {
        if (settingsRepo == null) settingsRepo = SettingsRepositoryImpl(appContext)
        return settingsRepo
    }

    @JvmStatic
    @Synchronized
    fun getBrowserRepository(appContext: Context): BrowserRepository? {
        if (browserRepository == null) browserRepository =
            BrowserRepositoryImpl(AppDatabase.getInstance(appContext))
        return browserRepository
    }
}
