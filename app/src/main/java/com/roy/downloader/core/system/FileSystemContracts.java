package com.roy.downloader.core.system;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.roy.downloader.R;
import com.roy.downloader.core.utils.Utils;
import com.roy.downloader.ui.filemanager.FileManagerConfig;
import com.roy.downloader.ui.filemanager.DialogFileManager;

public final class FileSystemContracts {
    private FileSystemContracts() {
    }

    /**
     * An {@link ActivityResultContract} to prompt the user to select a directory, returning the
     * user selection as a {@link Uri}. Apps can fully manage documents within the returned
     * directory.
     * <p>
     * The input is an optional {@link Uri} of the initial starting location.
     */
    public static class OpenDirectory extends ActivityResultContracts.OpenDocumentTree {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, @Nullable Uri input) {
            super.createIntent(context, input);

            Intent i = new Intent(context, DialogFileManager.class);
            String dirPath = null;
            if (input != null && Utils.isFileSystemPath(input)) {
                dirPath = input.getPath();
            }
            FileManagerConfig config = new FileManagerConfig(
                    dirPath,
                    context.getString(R.string.select_folder_to_save),
                    FileManagerConfig.DIR_CHOOSER_MODE
            );
            i.putExtra(DialogFileManager.TAG_CONFIG, config);
            return i;
        }
    }

    /**
     * An {@link ActivityResultContract} to prompt the user to open a document, receiving its
     * contents as a {@code file:/http:/content:} {@link Uri}.
     * <p>
     * The input is the mime types to filter by, e.g. {@code image/*}.
     */
    public static class OpenFile extends ActivityResultContracts.OpenDocument {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, @NonNull String[] input) {
            super.createIntent(context, input);

            Intent i = new Intent(context, DialogFileManager.class);
            FileManagerConfig config = new FileManagerConfig(
                    null,
                    null,
                    FileManagerConfig.FILE_CHOOSER_MODE
            );
            config.mimeType = input.length > 0 ? input[0] : null;
            i.putExtra(DialogFileManager.TAG_CONFIG, config);
            return i;
        }
    }
}
