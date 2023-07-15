package com.roy.downloader.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    @JvmField
    var detailTitleChanged = MutableLiveData<String>()
}
