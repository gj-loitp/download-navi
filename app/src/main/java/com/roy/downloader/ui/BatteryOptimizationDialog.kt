package com.roy.downloader.ui

import android.app.Dialog
import android.os.Bundle
import com.roy.downloader.R

class BatteryOptimizationDialog : BaseAlertDialog() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val title = getString(R.string.battery_optimization)
        val message = getString(R.string.disable_battery_optimization_summary)
        val positiveText = getString(R.string.disable)
        val negativeText = getString(R.string.no)
        return buildDialog(
            /* title = */ title,
            /* message = */message,
            /* view = */null,
            /* positiveText = */positiveText,
            /* negativeText = */negativeText,
            /* neutralText = */null,
            /* autoDismiss = */false
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(): BatteryOptimizationDialog {
            val frag = BatteryOptimizationDialog()
            val args = Bundle()
            frag.arguments = args
            return frag
        }
    }
}
