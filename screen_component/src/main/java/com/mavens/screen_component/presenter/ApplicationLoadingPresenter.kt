package com.mavens.screen_component.presenter

import android.util.Log
import com.mavens.screen_component.ApplicationLoadDataView
import com.mavens.screen_component.RequestTimeoutMonitor

/**
 * Created by azamat  on 10/19/20.
 */
abstract class ApplicationLoadingPresenter(private val requestTimeoutMonitor: RequestTimeoutMonitor) :
    ApplicationPresenter() {

    var applicationLoadDataView: ApplicationLoadDataView? = null
    private var onResumePerformer: (() -> Unit)? = null
    private var wasInitialized = false

    fun setLoadDataView(applicationLoadDataView: ApplicationLoadDataView) {
        this.applicationLoadDataView = applicationLoadDataView
    }

    override fun resume() {
        super.resume()
        invokeOnResumePerformer()
        requestTimeoutMonitor.startMonitoring()
    }

    override fun pause() {
        super.pause()
        requestTimeoutMonitor.removeListeners()
        requestTimeoutMonitor.stopMonitoring()
    }

    override fun destroy() {
        super.destroy()
        applicationLoadDataView = null
    }

    protected fun showLoadingView() {
        this.applicationLoadDataView?.showLoading()
    }

    private fun showLoadingProgressMessage(message: String) {
        showLoadingView()
        this.applicationLoadDataView?.showLoadingProgressMessage(message)
    }

    protected fun showViewLoadingWithProgressMessage(message: String) {
        showLoadingProgressMessage(message)
    }

    protected fun showViewLoadingWithProgressMessage(resourceId: Int) {
        this.applicationLoadDataView?.context()?.let {
            val message = it.getString(resourceId)
            showViewLoadingWithProgressMessage(message)
        }
    }

    protected fun hideViewLoading() {
        applicationLoadDataView?.hideLoading()
    }

    protected fun showErrorMessage(error: String) {
        this.applicationLoadDataView?.showError(error)
    }

    protected fun showErrorMessage(error: Throwable) {
        error.message?.let {
            Log.d("TOASTED_MESSAGE", it)
        }
        val errorMessage = getErrorMessageText(error)
        errorMessage?.let { showErrorMessage(it) }
    }

    protected fun showSuccessMessage(message: String) {
        this.applicationLoadDataView?.showSuccessMessage(message)
    }

    protected fun showWarningMessage(message: String) {
        this.applicationLoadDataView?.showWarningMessage(message)
    }

    protected fun performForOnce(performer: () -> Unit) {
        if (!wasInitialized) {
            performer.invoke()
        }

        wasInitialized = true
    }

    protected fun performAfterModelsInitialized(performer: () -> Unit) {
        if (wasInitialized) {
            performer.invoke()
        }
    }

    protected fun performOnResume(performer: () -> Unit) {
        applicationLoadDataView?.let {
            if (!isOnPause() && it.isFragmentVisible()) {
                performer.invoke()
                return
            }

            onResumePerformer = performer
        }
    }

    protected fun invokeOnResumePerformer() {
        onResumePerformer?.invoke()
        onResumePerformer = null
    }

    protected abstract fun getErrorMessageText(error: Throwable): String?
}