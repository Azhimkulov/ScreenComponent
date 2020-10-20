package com.mavens.screen_component.presenter

/**
 * Created by azamat  on 10/19/20.
 */
abstract class ApplicationPresenter:
    Presenter {
    private var onPause = false

    override fun start() {}

    override fun onViewCreated() {}

    override fun resume() { onPause = false}

    override fun pause() { onPause = true}

    override fun destroy() {}

    protected fun isOnPause(): Boolean {
        return onPause
    }
}