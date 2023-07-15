package com.roy.downloader.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.roy.downloader.R;

public class ThemedSwipeRefreshLayout extends SwipeRefreshLayout {
    public ThemedSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(new TypedValue().data, new int[]{R.attr.foreground, R.attr.colorSecondary});
        setColorSchemeColors(a.getColor(1, 0));
        setProgressBackgroundColorSchemeColor(a.getColor(0, 0));
        a.recycle();
    }
}
