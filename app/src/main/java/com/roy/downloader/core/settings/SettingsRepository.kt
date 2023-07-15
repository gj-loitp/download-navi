package com.roy.downloader.core.settings

import io.reactivex.Flowable

interface SettingsRepository {
    /*
     * Returns Flowable with key
     */
    fun observeSettingsChanged(): Flowable<String?>?

    /*
     * Appearance settings
     */
    fun theme(): Int
    fun theme(`val`: Int)
    fun progressNotify(): Boolean
    fun progressNotify(`val`: Boolean)
    fun finishNotify(): Boolean
    fun finishNotify(`val`: Boolean)
    fun pendingNotify(): Boolean
    fun pendingNotify(`val`: Boolean)
    fun notifySound(): String?
    fun notifySound(`val`: String?)
    fun playSoundNotify(): Boolean
    fun playSoundNotify(`val`: Boolean)
    fun ledIndicatorNotify(): Boolean
    fun ledIndicatorNotify(`val`: Boolean)
    fun vibrationNotify(): Boolean
    fun vibrationNotify(`val`: Boolean)
    fun ledIndicatorColorNotify(): Int
    fun ledIndicatorColorNotify(`val`: Int)

    /*
     * Behavior settings
     */
    fun unmeteredConnectionsOnly(): Boolean
    fun unmeteredConnectionsOnly(`val`: Boolean)
    fun enableRoaming(): Boolean
    fun enableRoaming(`val`: Boolean)
    fun autostart(): Boolean
    fun autostart(`val`: Boolean)
    fun cpuDoNotSleep(): Boolean
    fun cpuDoNotSleep(`val`: Boolean)
    fun onlyCharging(): Boolean
    fun onlyCharging(`val`: Boolean)
    fun batteryControl(): Boolean
    fun batteryControl(`val`: Boolean)
    fun customBatteryControl(): Boolean
    fun customBatteryControl(`val`: Boolean)
    fun customBatteryControlValue(): Int
    fun customBatteryControlValue(`val`: Int)
    fun timeout(): Int
    fun timeout(`val`: Int)
    fun replaceDuplicateDownloads(): Boolean
    fun replaceDuplicateDownloads(`val`: Boolean)
    fun autoConnect(): Boolean
    fun autoConnect(`val`: Boolean)
    fun userAgent(): String?
    fun userAgent(`val`: String?)

    /*
     * Limitation settings
     */
    fun maxActiveDownloads(): Int
    fun maxActiveDownloads(`val`: Int)
    fun maxDownloadRetries(): Int
    fun maxDownloadRetries(`val`: Int)
    fun speedLimit(): Int
    fun speedLimit(`val`: Int)

    /*
     * Storage settings
     */
    fun saveDownloadsIn(): String?
    fun saveDownloadsIn(`val`: String?)
    fun moveAfterDownload(): Boolean
    fun moveAfterDownload(`val`: Boolean)
    fun moveAfterDownloadIn(): String?
    fun moveAfterDownloadIn(`val`: String?)
    fun deleteFileIfError(): Boolean
    fun deleteFileIfError(`val`: Boolean)
    fun preallocateDiskSpace(): Boolean
    fun preallocateDiskSpace(`val`: Boolean)

    /*
     * Browser settings
     */
    fun browserAllowJavaScript(): Boolean
    fun browserAllowJavaScript(`val`: Boolean)
    fun browserAllowPopupWindows(): Boolean
    fun browserAllowPopupWindows(`val`: Boolean)
    fun browserLauncherIcon(): Boolean
    fun browserLauncherIcon(`val`: Boolean)
    fun browserEnableCaching(): Boolean
    fun browserEnableCaching(`val`: Boolean)
    fun browserEnableCookies(): Boolean
    fun browserEnableCookies(`val`: Boolean)
    fun browserDisableFromSystem(): Boolean
    fun browserDisableFromSystem(`val`: Boolean)
    fun browserStartPage(): String?
    fun browserStartPage(`val`: String?)
    fun browserBottomAddressBar(): Boolean
    fun browserBottomAddressBar(`val`: Boolean)
    fun browserDoNotTrack(): Boolean
    fun browserDoNotTrack(`val`: Boolean)
    fun browserSearchEngine(): String?
    fun browserSearchEngine(`val`: String?)
    fun browserHideMenuIcon(): Boolean
    fun browserHideMenuIcon(`val`: Boolean)
    fun askDisableBatteryOptimization(`val`: Boolean)
    fun askDisableBatteryOptimization(): Boolean
    fun askNotificationPermission(): Boolean
    fun askNotificationPermission(`val`: Boolean)
}
