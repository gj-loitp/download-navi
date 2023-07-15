package com.roy.downloader.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.roy.downloader.R;

/*
 * Adds "Copy" item in share dialog.
 */

public class SendTextToClipboard extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Activity.CLIPBOARD_SERVICE);
            ClipData clip;

            CharSequence text = intent.getCharSequenceExtra(Intent.EXTRA_TEXT);

            clip = ClipData.newPlainText(intent.getType(), text);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(getApplicationContext(), R.string.text_copied_to_clipboard, Toast.LENGTH_SHORT).show();
        }

        finish();
        overridePendingTransition(0, 0);
    }
}
