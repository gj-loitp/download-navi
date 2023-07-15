package com.roy.downloader.ui.browser.bookmarks;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.roy.downloader.R;
import com.roy.downloader.databinding.VItemBrowserBookmarksListBinding;
import com.roy.downloader.ui.Selectable;

import java.util.Collections;
import java.util.List;

public class BrowserBookmarksAdapter extends ListAdapter<BrowserBookmarkItem, BrowserBookmarksAdapter.ViewHolder>
        implements Selectable<BrowserBookmarkItem> {
    private final ClickListener listener;
    private SelectionTracker<BrowserBookmarkItem> selectionTracker;

    protected BrowserBookmarksAdapter(@NonNull ClickListener listener) {
        super(diffCallback);

        this.listener = listener;
    }

    public void setSelectionTracker(SelectionTracker<BrowserBookmarkItem> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        VItemBrowserBookmarksListBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.v_item_browser_bookmarks_list,
                parent,
                false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BrowserBookmarkItem item = getItem(position);

        if (selectionTracker != null)
            holder.setSelected(selectionTracker.isSelected(item));

        holder.bind(item, listener);
    }

    @Override
    public void submitList(@Nullable List<BrowserBookmarkItem> list) {
        if (list != null)
            Collections.sort(list);

        super.submitList(list);
    }

    @Override
    public BrowserBookmarkItem getItemKey(int position) {
        if (position < 0 || position >= getCurrentList().size())
            return null;

        return getItem(position);
    }

    @Override
    public int getItemPosition(BrowserBookmarkItem key) {
        return getCurrentList().indexOf(key);
    }

    interface ViewHolderWithDetails {
        ItemDetails getItemDetails();
    }

    public interface ClickListener {
        void onItemClicked(@NonNull BrowserBookmarkItem item);

        void onItemMenuClicked(int menuId, @NonNull BrowserBookmarkItem item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements ViewHolderWithDetails {
        /* For selection support */
        private final VItemBrowserBookmarksListBinding binding;
        private BrowserBookmarkItem selectionKey;
        private boolean isSelected;

        public ViewHolder(VItemBrowserBookmarksListBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        void bind(BrowserBookmarkItem item, ClickListener listener) {
            selectionKey = item;

            binding.menu.setOnClickListener((v) -> {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.menu_browser_bookmark_popup);
                popup.setOnMenuItemClickListener((MenuItem menuItem) -> {
                    if (listener != null)
                        listener.onItemMenuClicked(menuItem.getItemId(), item);
                    return true;
                });
                popup.show();
            });

            itemView.setOnClickListener((v) -> {
                /* Skip selecting and deselecting */
                if (isSelected)
                    return;

                if (listener != null)
                    listener.onItemClicked(item);
            });

            binding.name.setText(item.name);
            binding.url.setText(item.url);

            TypedArray a = itemView.getContext().obtainStyledAttributes(new TypedValue().data, new int[]{
                    R.attr.selectableColor,
                    R.attr.defaultRectRipple
            });
            Drawable d;
            if (isSelected)
                d = a.getDrawable(0);
            else
                d = a.getDrawable(1);
            if (d != null)
                itemView.setBackground(d);
            a.recycle();
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        @Override
        public ItemDetails getItemDetails() {
            return new ItemDetails(selectionKey, getBindingAdapterPosition());
        }
    }

    public static final DiffUtil.ItemCallback<BrowserBookmarkItem> diffCallback = new DiffUtil.ItemCallback<BrowserBookmarkItem>() {
        @Override
        public boolean areContentsTheSame(@NonNull BrowserBookmarkItem oldItem,
                                          @NonNull BrowserBookmarkItem newItem) {
            return oldItem.equalsContent(newItem);
        }

        @Override
        public boolean areItemsTheSame(@NonNull BrowserBookmarkItem oldItem,
                                       @NonNull BrowserBookmarkItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    /*
     * Selection support stuff
     */

    public static final class KeyProvider extends ItemKeyProvider<BrowserBookmarkItem> {
        private final Selectable<BrowserBookmarkItem> selectable;

        KeyProvider(Selectable<BrowserBookmarkItem> selectable) {
            super(SCOPE_MAPPED);

            this.selectable = selectable;
        }

        @Nullable
        @Override
        public BrowserBookmarkItem getKey(int position) {
            return selectable.getItemKey(position);
        }

        @Override
        public int getPosition(@NonNull BrowserBookmarkItem key) {
            return selectable.getItemPosition(key);
        }
    }

    public static final class ItemDetails extends ItemDetailsLookup.ItemDetails<BrowserBookmarkItem> {
        private final BrowserBookmarkItem selectionKey;
        private final int adapterPosition;

        ItemDetails(BrowserBookmarkItem selectionKey, int adapterPosition) {
            this.selectionKey = selectionKey;
            this.adapterPosition = adapterPosition;
        }

        @Nullable
        @Override
        public BrowserBookmarkItem getSelectionKey() {
            return selectionKey;
        }

        @Override
        public int getPosition() {
            return adapterPosition;
        }
    }

    public static class ItemLookup extends ItemDetailsLookup<BrowserBookmarkItem> {
        private final RecyclerView recyclerView;

        ItemLookup(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Nullable
        @Override
        public ItemDetails<BrowserBookmarkItem> getItemDetails(@NonNull MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                if (viewHolder instanceof ViewHolder)
                    return ((ViewHolder) viewHolder).getItemDetails();
            }

            return null;
        }
    }
}
