package com.roy.downloader.core.storage

import com.roy.downloader.core.model.data.entity.BrowserBookmark
import io.reactivex.Flowable
import io.reactivex.Single

class BrowserRepositoryImpl(private val db: AppDatabase) : BrowserRepository {
    override fun addBookmark(bookmark: BrowserBookmark): Single<Long> {
        return db.browserBookmarksDao().add(bookmark)
    }

    override fun deleteBookmarks(bookmarks: List<BrowserBookmark>): Single<Int> {
        return db.browserBookmarksDao().delete(bookmarks)
    }

    override fun updateBookmark(bookmark: BrowserBookmark): Single<Int> {
        return db.browserBookmarksDao().update(bookmark)
    }

    override fun getBookmarkByUrlSingle(url: String): Single<BrowserBookmark> {
        return db.browserBookmarksDao().getByUrlSingle(url)
    }

    override fun observeAllBookmarks(): Flowable<List<BrowserBookmark>> {
        return db.browserBookmarksDao().observeAll()
    }
}
