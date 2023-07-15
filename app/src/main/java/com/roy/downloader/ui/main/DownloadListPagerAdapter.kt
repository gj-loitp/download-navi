package com.roy.downloader.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OffscreenPageLimit

class DownloadListPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        /* Stubs */
        return when (position) {
            QUEUED_FRAG_POS -> QueuedDownloadsFragment.newInstance()
            COMPLETED_FRAG_POS -> FinishedDownloadsFragment.newInstance()
            else -> Fragment()
        }
    }

    override fun getItemCount(): Int {
        return NUM_FRAGMENTS
    }

    companion object {
        @OffscreenPageLimit
        const val NUM_FRAGMENTS = 2
        const val QUEUED_FRAG_POS = 0
        const val COMPLETED_FRAG_POS = 1
    }
}
