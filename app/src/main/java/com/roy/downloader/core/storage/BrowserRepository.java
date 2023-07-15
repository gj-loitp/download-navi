package com.roy.downloader.core.storage;

import com.roy.downloader.core.model.data.entity.BrowserBookmark;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface BrowserRepository {
    Single<Long> addBookmark(BrowserBookmark bookmark);

    Single<Integer> deleteBookmarks(List<BrowserBookmark> bookmarks);

    Single<Integer> updateBookmark(BrowserBookmark bookmark);

    Single<BrowserBookmark> getBookmarkByUrlSingle(String url);

    Flowable<List<BrowserBookmark>> observeAllBookmarks();
}
