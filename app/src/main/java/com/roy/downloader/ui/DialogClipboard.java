package com.roy.downloader.ui;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.roy.downloader.R;
import com.roy.downloader.core.utils.Utils;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class DialogClipboard extends DialogFragment {
    @SuppressWarnings("unused")
    private static final String TAG = DialogClipboard.class.getSimpleName();

    private AppCompatActivity activity;
    private ArrayAdapter<CharSequence> adapter;
    private SharedViewModel viewModel;

    public static class SharedViewModel extends androidx.lifecycle.ViewModel {
        private final PublishSubject<Item> selectedItemSubject = PublishSubject.create();

        public Observable<Item> observeSelectedItem() {
            return selectedItemSubject;
        }

        public void sendSelectedItem(Item item) {
            selectedItemSubject.onNext(item);
        }
    }

    public static class Item {
        public String dialogTag;
        public String str;

        public Item(String dialogTag, String str) {
            this.dialogTag = dialogTag;
            this.str = str;
        }
    }

    public static DialogClipboard newInstance() {
        DialogClipboard frag = new DialogClipboard();

        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AppCompatActivity) activity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (activity == null) activity = (AppCompatActivity) getActivity();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity).setTitle(R.string.clipboard).setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

        ArrayList<CharSequence> clipboardText = getClipboardText();
        adapter = new ArrayAdapter<>(activity, R.layout.v_item_clipboard_list);
        adapter.addAll(clipboardText);

        dialogBuilder.setAdapter(adapter, (dialog, which) -> {
            CharSequence item = adapter.getItem(which);
            if (item != null) viewModel.sendSelectedItem(new Item(getTag(), item.toString()));
        });

        return dialogBuilder.create();
    }

    private ArrayList<CharSequence> getClipboardText() {
        ArrayList<CharSequence> clipboardText = new ArrayList<>();

        ClipData clip = Utils.getClipData(activity.getApplicationContext());
        if (clip == null) return clipboardText;

        for (int i = 0; i < clip.getItemCount(); i++) {
            CharSequence item = clip.getItemAt(i).getText();
            if (item == null) continue;
            clipboardText.add(item);
        }

        return clipboardText;
    }
}
