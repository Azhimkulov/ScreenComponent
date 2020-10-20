package com.mavens.screen_component

import com.mavens.screen_component.LoadingTimeoutListener

/**
 * Created by azamat  on 10/19/20.
 */
interface RequestTimeoutMonitor {
    fun startMonitoring()
    fun stopMonitoring()
    fun notifyLoadingShown()
    fun notifyLoadingHidden()
    fun addListener(loadingTimeoutListener: LoadingTimeoutListener)
    fun removeListener(loadingTimeoutListener: LoadingTimeoutListener)
    fun removeListeners()
}