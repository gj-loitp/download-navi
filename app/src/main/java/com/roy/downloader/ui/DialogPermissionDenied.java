package com.roy.downloader.ui;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.roy.downloader.R;

public class DialogPermissionDenied extends BaseAlertDialog {
    public static DialogPermissionDenied newInstance() {
        DialogPermissionDenied frag = new DialogPermissionDenied();

        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        String title = getString(R.string.perm_denied_title);
        String message = getString(R.string.perm_denied_warning);
        String positiveText = getString(R.string.yes);
        String negativeText = getString(R.string.no);

        return buildDialog(title, message, null, positiveText, negativeText, null, false);
    }
}
