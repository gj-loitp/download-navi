package com.roy.downloader.ui.details;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class DownloadDetailsMutableParams extends BaseObservable {
    private String url;
    private String fileName;
    private String description;
    private Uri dirPath;
    private boolean unmeteredConnectionsOnly = false;
    private boolean retry = false;
    private String checksum;

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        notifyPropertyChanged(BR.fileName);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public Uri getDirPath() {
        return dirPath;
    }

    public void setDirPath(Uri dirPath) {
        this.dirPath = dirPath;
        notifyPropertyChanged(BR.dirPath);
    }

    @Bindable
    public boolean isUnmeteredConnectionsOnly() {
        return unmeteredConnectionsOnly;
    }

    public void setUnmeteredConnectionsOnly(boolean unmeteredConnectionsOnly) {
        this.unmeteredConnectionsOnly = unmeteredConnectionsOnly;
        notifyPropertyChanged(BR.unmeteredConnectionsOnly);
    }

    @Bindable
    public boolean isRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
        notifyPropertyChanged(BR.retry);
    }

    @Bindable
    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
        notifyPropertyChanged(BR.checksum);
    }

    @NonNull
    @Override
    public String toString() {
        return "DownloadDetailsMutableParams{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", description='" + description + '\'' +
                ", dirPath=" + dirPath +
                ", unmeteredConnectionsOnly=" + unmeteredConnectionsOnly +
                ", retry=" + retry +
                ", checksum='" + checksum + '\'' +
                '}';
    }
}
