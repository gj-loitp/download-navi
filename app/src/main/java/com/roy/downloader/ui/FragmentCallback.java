package com.roy.downloader.ui;

/*
 * The basic callback interface with codes and functions, returned by fragments.
 */

import android.content.Intent;

public interface FragmentCallback {
    @SuppressWarnings("unused")
    String TAG = FragmentCallback.class.getSimpleName();

    enum ResultCode {
        OK, CANCEL, BACK
    }

    void fragmentFinished(Intent intent, ResultCode code);
}