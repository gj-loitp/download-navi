package com.roy.downloader.core.storage;

import androidx.annotation.NonNull;

import com.roy.downloader.core.model.data.entity.BrowserBookmark;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class BrowserRepositoryImpl implements BrowserRepository {
    private final AppDatabase db;

    public BrowserRepositoryImpl(@NonNull AppDatabase db) {
        this.db = db;
    }

    @Override
    public Single<Long> addBookmark(BrowserBookmark bookmark) {
        return db.browserBookmarksDao().add(bookmark);
    }

    @Override
    public Single<Integer> deleteBookmarks(List<BrowserBookmark> bookmarks) {
        return db.browserBookmarksDao().delete(bookmarks);
    }

    @Override
    public Single<Integer> updateBookmark(BrowserBookmark bookmark) {
        return db.browserBookmarksDao().update(bookmark);
    }

    @Override
    public Single<BrowserBookmark> getBookmarkByUrlSingle(String url) {
        return db.browserBookmarksDao().getByUrlSingle(url);
    }

    @Override
    public Flowable<List<BrowserBookmark>> observeAllBookmarks() {
        return db.browserBookmarksDao().observeAll();
    }
}
