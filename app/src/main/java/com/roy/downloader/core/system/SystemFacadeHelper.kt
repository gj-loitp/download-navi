package com.roy.downloader.core.system

import android.content.Context

object SystemFacadeHelper {
    private var systemFacade: SystemFacade? = null
    private var fileSystemFacade: FileSystemFacade? = null

    @JvmStatic
    @Synchronized
    fun getSystemFacade(appContext: Context): SystemFacade? {
        if (systemFacade == null) systemFacade = SystemFacadeImpl(appContext)
        return systemFacade
    }

    @JvmStatic
    @Synchronized
    fun getFileSystemFacade(appContext: Context): FileSystemFacade? {
        if (fileSystemFacade == null) fileSystemFacade = FileSystemFacadeImpl(
            SysCallImpl(),
            FsModuleResolverImpl(appContext), appContext
        )
        return fileSystemFacade
    }
}
