package com.roy.downloader.ui

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.roy.downloader.R

/*
 * Adds "Copy" item in share dialog.
 */
class SendTextToClipboard : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData
            val text = intent.getCharSequenceExtra(Intent.EXTRA_TEXT)
            clip = ClipData.newPlainText(intent.type, text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                applicationContext, R.string.text_copied_to_clipboard, Toast.LENGTH_SHORT
            ).show()
        }
        finish()
        overridePendingTransition(0, 0)
    }
}
