package com.roy.downloader.ui.browser.bookmarks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.roy.downloader.core.RepositoryHelper;
import com.roy.downloader.core.model.data.entity.BrowserBookmark;
import com.roy.downloader.core.storage.BrowserRepository;

import java.util.Collections;

import io.reactivex.Completable;
import io.reactivex.Single;

public class EditBookmarkViewModel extends AndroidViewModel {
    BrowserRepository repo;

    public EditBookmarkViewModel(@NonNull Application application) {
        super(application);

        repo = RepositoryHelper.getBrowserRepository(application);
    }

    Completable applyChanges(@NonNull BrowserBookmark oldBookmark,
                             @NonNull BrowserBookmark newBookmark) {
        if (!oldBookmark.url.equals(newBookmark.url)) {
            return repo.deleteBookmarks(Collections.singletonList(oldBookmark))
                    .flatMap((__) -> repo.addBookmark(newBookmark))
                    .ignoreElement();
        } else {
            return repo.updateBookmark(newBookmark).ignoreElement();
        }
    }

    Single<Integer> deleteBookmark(@NonNull BrowserBookmark bookmark) {
        return repo.deleteBookmarks(Collections.singletonList(bookmark));
    }
}
