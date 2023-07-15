package com.roy.downloader.ui.browser.bookmarks

import com.roy.downloader.core.model.data.entity.BrowserBookmark

class BrowserBookmarkItem(bookmark: BrowserBookmark) :
    BrowserBookmark(bookmark.url, bookmark.name, bookmark.dateAdded),
    Comparable<BrowserBookmarkItem> {
    override fun compareTo(o: BrowserBookmarkItem): Int {
        return o.dateAdded.compareTo(dateAdded)
    }

    fun equalsContent(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as BrowserBookmark
        if (dateAdded != that.dateAdded) return false
        return if (url != that.url) false else name == that.name
    }
}
