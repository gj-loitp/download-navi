package com.roy.downloader.ui.browser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.roy.downloader.R;
import com.roy.downloader.databinding.DlgBrowserContextMenuDialogBinding;
import com.roy.downloader.ui.FragmentCallback;

public class DialogBrowserContextMenu extends BottomSheetDialogFragment {
    @SuppressWarnings("unused")
    private static final String TAG = DialogBrowserContextMenu.class.getSimpleName();

    public static final String TAG_URL = "url";
    public static final String TAG_ACTION_SHARE = "action_share";
    public static final String TAG_ACTION_DOWNLOAD = "action_download";
    public static final String TAG_ACTION_COPY = "action_copy";

    private AppCompatActivity activity;
    private DlgBrowserContextMenuDialogBinding binding;

    public static DialogBrowserContextMenu newInstance(@NonNull String url) {
        DialogBrowserContextMenu frag = new DialogBrowserContextMenu();

        Bundle args = new Bundle();
        args.putString(TAG_URL, url);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AppCompatActivity)
            activity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dlg_browser_context_menu_dialog, container, true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (activity == null)
            activity = (AppCompatActivity) getActivity();

        /* Make full height */
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                assert dialog != null;
                FrameLayout bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setPeekHeight(0);
            }
        });

        assert getArguments() != null;
        String url = getArguments().getString(TAG_URL);
        if (url != null)
            binding.title.setText(url);

        binding.share.setOnClickListener(onItemClickListener);
        binding.downloadFromLink.setOnClickListener(onItemClickListener);
        binding.copyLink.setOnClickListener(onItemClickListener);
    }

    private final View.OnClickListener onItemClickListener = (v) -> {
        Intent i = new Intent();
        assert getArguments() != null;
        i.putExtra(TAG_URL, getArguments().getString(TAG_URL));

        switch (v.getId()) {
            case R.id.share -> i.setAction(TAG_ACTION_SHARE);
            case R.id.downloadFromLink -> i.setAction(TAG_ACTION_DOWNLOAD);
            case R.id.copyLink -> i.setAction(TAG_ACTION_COPY);
        }

        finish(i, FragmentCallback.ResultCode.OK);
    };

    private void finish(Intent intent, FragmentCallback.ResultCode code) {
        dismiss();
        ((FragmentCallback) activity).fragmentFinished(intent, code);
    }
}
