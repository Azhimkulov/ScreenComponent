package com.mavens.screen_component

/**
 * Created by azamat  on 10/19/20.
 */
interface LoadingTimeoutListener {
    fun handleLoadingTimedOut(monitor: RequestTimeoutMonitor, loadingTimeInSeconds: Long)
}