package com.roy.downloader.ui.errorreport;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.roy.downloader.R;
import com.roy.downloader.databinding.DlgDialogErrorBinding;
import com.roy.downloader.ui.BaseAlertDialog;

public class DialogErrorReport extends BaseAlertDialog {
    @SuppressWarnings("unused")
    private static final String TAG = DialogErrorReport.class.getSimpleName();

    protected static final String TAG_DETAIL_ERROR = "detail_error";

    /* In the absence of any parameter need set 0 or null */

    public static DialogErrorReport newInstance(String title, String message,
                                                String detailError) {
        DialogErrorReport frag = new DialogErrorReport();

        Bundle args = new Bundle();
        args.putString(TAG_TITLE, title);
        args.putString(TAG_MESSAGE, message);
        args.putString(TAG_DETAIL_ERROR, detailError);

        frag.setArguments(args);

        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Bundle args = getArguments();
        assert args != null;
        String title = args.getString(TAG_TITLE);
        String message = args.getString(TAG_MESSAGE);
        String positiveText = getString(R.string.report);
        String negativeText = getString(R.string.cancel);
        String detailError = args.getString(TAG_DETAIL_ERROR);

        LayoutInflater i = LayoutInflater.from(getActivity());
        DlgDialogErrorBinding binding = DataBindingUtil.inflate(i, R.layout.dlg_dialog_error, null, false);
        binding.setDetailError(detailError);

        initLayoutView(binding);

        return buildDialog(title, message, binding.getRoot(),
                positiveText, negativeText, null, false);
    }

    private void initLayoutView(DlgDialogErrorBinding binding) {
        binding.expansionHeader.setOnClickListener((View view) -> {
            binding.expandableLayout.toggle();
            binding.expansionHeader.toggleExpand();
        });
    }
}
