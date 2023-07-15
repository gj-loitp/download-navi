package com.roy.downloader.ui.errorreport;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.roy.downloader.R;
import com.roy.downloader.ui.BaseAlertDialog;

import org.acra.ReportField;
import org.acra.data.CrashReportData;
import org.acra.dialog.CrashReportDialogHelper;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ActivityErrorReport extends AppCompatActivity {
    @SuppressWarnings("unused")
    private static final String TAG = ActivityErrorReport.class.getSimpleName();

    private static final String TAG_ERROR_DIALOG = "error_dialog";
    private DialogErrorReport errDialog;
    private BaseAlertDialog.SharedViewModel dialogViewModel;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private CrashReportDialogHelper helper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper = new CrashReportDialogHelper(this, getIntent());
        dialogViewModel = new ViewModelProvider(this).get(BaseAlertDialog.SharedViewModel.class);
        errDialog = (DialogErrorReport) getSupportFragmentManager().findFragmentByTag(TAG_ERROR_DIALOG);

        if (errDialog == null) {
            errDialog = DialogErrorReport.newInstance(
                    getString(R.string.error),
                    getString(R.string.app_error_occurred),
                    getStackTrace());

            errDialog.show(getSupportFragmentManager(), TAG_ERROR_DIALOG);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        disposables.clear();
    }

    @Override
    public void onStart() {
        super.onStart();

        subscribeAlertDialog();
    }

    private void subscribeAlertDialog() {
        Disposable d = dialogViewModel.observeEvents().subscribe(this::handleAlertDialogEvent);
        disposables.add(d);
    }

    private void handleAlertDialogEvent(BaseAlertDialog.Event event) {
        if (event.dialogTag == null || !event.dialogTag.equals(TAG_ERROR_DIALOG) || errDialog == null)
            return;
        switch (event.type) {
            case POSITIVE_BUTTON_CLICKED -> {
                Dialog dialog = errDialog.getDialog();
                if (dialog != null) {
                    TextInputEditText editText = dialog.findViewById(R.id.comment);
                    Editable e = editText.getText();
                    String comment = (e == null ? null : e.toString());

                    helper.sendCrash(comment, null);
                    finish();
                }
            }
            case NEGATIVE_BUTTON_CLICKED -> {
                helper.cancelReports();
                finish();
            }
        }
    }

    @Override
    public void finish() {
        if (errDialog != null)
            errDialog.dismiss();

        super.finish();
    }

    private String getStackTrace() {
        Intent i = getIntent();
        if (i == null)
            return null;

        CrashReportData crashReportData;
        try {
            crashReportData = helper.getReportData();

        } catch (IOException e) {
            return null;
        }

        return crashReportData.getString(ReportField.STACK_TRACE);
    }
}
