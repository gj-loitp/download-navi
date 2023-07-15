package com.roy.downloader.ui.browser.bookmarks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.roy.downloader.core.RepositoryHelper;
import com.roy.downloader.core.model.data.entity.BrowserBookmark;
import com.roy.downloader.core.storage.BrowserRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class BrowserBookmarksViewModel extends AndroidViewModel {
    private final BrowserRepository repo;

    public BrowserBookmarksViewModel(@NonNull Application application) {
        super(application);

        repo = RepositoryHelper.getBrowserRepository(application);
    }

    Flowable<List<BrowserBookmark>> observeBookmarks() {
        return repo.observeAllBookmarks();
    }

    Single<Integer> deleteBookmarks(@NonNull List<BrowserBookmark> bookmarks) {
        return repo.deleteBookmarks(bookmarks);
    }
}
