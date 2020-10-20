package com.mavens.screen_component

import android.content.Context

/**
 * Created by azamat  on 10/19/20.
 */
interface ApplicationLoadDataView {
    fun showLoading()
    fun showLoadingProgressMessage(message: String)
    fun hideLoading()
    fun showError(message:String)
    fun showSuccessMessage(message: String)
    fun showWarningMessage(message: String)

    fun context(): Context?
    fun isFragmentVisible():Boolean
}