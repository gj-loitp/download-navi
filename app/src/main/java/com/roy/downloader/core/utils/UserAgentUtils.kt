package com.roy.downloader.core.utils

import com.roy.downloader.core.model.data.entity.UserAgent

object UserAgentUtils {
    @JvmField
    val defaultUserAgents = arrayOf(
        UserAgent("Mozilla/5.0 (Linux; U; Android 4.1; en-us; DV Build/Donut)"),
        UserAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)"),
        UserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"),
        UserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0"),
        UserAgent("Opera/9.80 (Windows NT 6.1) Presto/2.12.388 Version/12.17"),
        UserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/603.3.8 (KHTML, like Gecko) Version/10.1.2 Safari/603.3.8")
    )
}
