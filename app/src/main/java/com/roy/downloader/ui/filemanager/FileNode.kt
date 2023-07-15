package com.roy.downloader.ui.filemanager

import java.io.Serializable

/*
 * The interface with basic functions for a file object.
 */   interface FileNode<F> : Comparable<F> {
    object Type : Serializable {
        @JvmField
        var DIR = 0

        @JvmField
        var FILE = 1
    }

    var name: String?
    var type: Int
    override fun compareTo(other: F): Int
}
