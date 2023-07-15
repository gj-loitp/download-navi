package com.roy.downloader.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/*
 * A RecyclerView with empty view.
 */
class EmptyRecyclerView : RecyclerView {
    private var emptyView: View? = null
    private val observer: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            checkEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkEmpty()
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(
        context, attrs
    )

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context, attrs, defStyle
    )

    fun checkEmpty() {
        if (emptyView != null && adapter != null) {
            val isEmptyViewVisible = adapter?.itemCount == 0
            emptyView?.visibility =
                if (isEmptyViewVisible) VISIBLE else GONE
            visibility = if (isEmptyViewVisible) GONE else VISIBLE
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)
        checkEmpty()
    }

    fun setEmptyView(emptyView: View?) {
        this.emptyView = emptyView
        checkEmpty()
    }
}
