package com.roy.downloader.ui.filemanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FileManagerViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context context;
    private final FileManagerConfig config;
    private final String startDir;

    public FileManagerViewModelFactory(@NonNull Context context, FileManagerConfig config, String startDir) {
        this.context = context;
        this.config = config;
        this.startDir = startDir;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FileManagerViewModel.class))
            return (T) new FileManagerViewModel(context, config, startDir);

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
