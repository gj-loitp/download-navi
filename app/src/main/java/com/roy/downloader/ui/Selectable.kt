package com.roy.downloader.ui

interface Selectable<T> {
    fun getItemKey(position: Int): T
    fun getItemPosition(key: T): Int
}
