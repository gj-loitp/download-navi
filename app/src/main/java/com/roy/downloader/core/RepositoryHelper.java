package com.roy.downloader.core;

import android.content.Context;

import androidx.annotation.NonNull;

import com.roy.downloader.core.settings.SettingsRepository;
import com.roy.downloader.core.settings.SettingsRepositoryImpl;
import com.roy.downloader.core.storage.AppDatabase;
import com.roy.downloader.core.storage.BrowserRepository;
import com.roy.downloader.core.storage.BrowserRepositoryImpl;
import com.roy.downloader.core.storage.DataRepository;
import com.roy.downloader.core.storage.DataRepositoryImpl;

public class RepositoryHelper {
    private static DataRepositoryImpl dataRepo;
    private static SettingsRepositoryImpl settingsRepo;
    private static BrowserRepository browserRepository;

    public synchronized static DataRepository getDataRepository(@NonNull Context appContext) {
        if (dataRepo == null)
            dataRepo = new DataRepositoryImpl(appContext,
                    AppDatabase.getInstance(appContext));

        return dataRepo;
    }

    public synchronized static SettingsRepository getSettingsRepository(@NonNull Context appContext) {
        if (settingsRepo == null)
            settingsRepo = new SettingsRepositoryImpl(appContext);

        return settingsRepo;
    }

    public synchronized static BrowserRepository getBrowserRepository(@NonNull Context appContext) {
        if (browserRepository == null)
            browserRepository = new BrowserRepositoryImpl(AppDatabase.getInstance(appContext));

        return browserRepository;
    }
}
