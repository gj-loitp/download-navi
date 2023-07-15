package com.roy.downloader.ui

import android.app.Dialog
import android.os.Bundle
import com.roy.downloader.R

class DialogPermissionDenied : BaseAlertDialog() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val title = getString(R.string.perm_denied_title)
        val message = getString(R.string.perm_denied_warning)
        val positiveText = getString(R.string.yes)
        val negativeText = getString(R.string.no)
        return buildDialog(title, message, null, positiveText, negativeText, null, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(): DialogPermissionDenied {
            val frag = DialogPermissionDenied()
            val args = Bundle()
            frag.arguments = args
            return frag
        }
    }
}