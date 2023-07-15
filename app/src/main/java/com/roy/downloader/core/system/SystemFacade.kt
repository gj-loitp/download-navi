package com.roy.downloader.core.system

import android.net.NetworkCapabilities
import android.net.NetworkInfo

interface SystemFacade {
    val activeNetworkInfo: NetworkInfo?
    val networkCapabilities: NetworkCapabilities?
    val isActiveNetworkMetered: Boolean
    val systemUserAgent: String?
}
