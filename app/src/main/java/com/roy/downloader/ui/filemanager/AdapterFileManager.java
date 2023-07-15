package com.roy.downloader.ui.filemanager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.roy.downloader.R;
import com.roy.downloader.core.system.FileSystemFacade;
import com.roy.downloader.core.system.SystemFacadeHelper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * The adapter for directory or file chooser dialog.
 */

public class AdapterFileManager extends ListAdapter<FileManagerNode, AdapterFileManager.ViewHolder> {
    @SuppressWarnings("unused")
    private static final String TAG = AdapterFileManager.class.getSimpleName();

    private final ViewHolder.ClickListener clickListener;
    private final List<String> highlightFileTypes;

    private static final Comparator<FileManagerNode> directoryFirstCmp = (n1, n2) -> {
        int byName = n1.compareTo(n2);
        int directoryFirst = Boolean.compare(n2.isDirectory(), n1.isDirectory());

        return (directoryFirst == 0 ? byName : directoryFirst);
    };

    public AdapterFileManager(List<String> highlightFileTypes, ViewHolder.ClickListener clickListener) {
        super(diffCallback);

        this.clickListener = clickListener;
        this.highlightFileTypes = highlightFileTypes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_item_filemanager, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), highlightFileTypes, clickListener);
    }

    @Override
    public void submitList(@Nullable List<FileManagerNode> list) {
        if (list != null)
            Collections.sort(list, directoryFirstCmp);

        super.submitList(list);
    }

    public static final DiffUtil.ItemCallback<FileManagerNode> diffCallback = new DiffUtil.ItemCallback<FileManagerNode>() {
        @Override
        public boolean areContentsTheSame(@NonNull FileManagerNode oldItem,
                                          @NonNull FileManagerNode newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(@NonNull FileManagerNode oldItem,
                                       @NonNull FileManagerNode newItem) {
            return oldItem.equals(newItem);
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fileName;
        private final ImageView fileIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            fileName = itemView.findViewById(R.id.fileName);
            fileIcon = itemView.findViewById(R.id.fileIcon);
        }

        void bind(FileManagerNode item, List<String> highlightFileTypes, ClickListener listener) {
            Context context = itemView.getContext();

            itemView.setOnClickListener((v) -> {
                if (listener != null)
                    listener.onItemClicked(item);
            });

            itemView.setEnabled(item.isEnabled());
            if (item.isEnabled()) {
                FileSystemFacade fs = SystemFacadeHelper.getFileSystemFacade(context);
                if (highlightFileTypes != null && highlightFileTypes.contains(fs.getExtension(item.getName()))) {
                    fileName.setTextColor(ContextCompat.getColor(context, R.color.accent));

                } else {
                    TypedArray a = context.obtainStyledAttributes(new TypedValue().data,
                            new int[]{android.R.attr.textColorPrimary});
                    fileName.setTextColor(a.getColor(0, 0));
                    a.recycle();
                }

            } else {
                TypedArray a = context.obtainStyledAttributes(new TypedValue().data,
                        new int[]{android.R.attr.textColorSecondary});
                fileName.setTextColor(a.getColor(0, 0));
                a.recycle();
            }

            fileName.setText(item.getName());

            if (item.isDirectory()) {
                fileIcon.setImageResource(R.drawable.ic_folder_grey600_24dp);
                fileIcon.setContentDescription(context.getString(R.string.folder));

            } else {
                fileIcon.setImageResource(R.drawable.ic_file_grey600_24dp);
                fileIcon.setContentDescription(context.getString(R.string.file));
            }
        }

        public interface ClickListener {
            void onItemClicked(FileManagerNode item);
        }
    }
}
