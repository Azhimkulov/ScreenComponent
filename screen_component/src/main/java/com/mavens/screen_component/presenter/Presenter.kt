package com.mavens.screen_component.presenter

/**
 * Created by azamat  on 10/19/20.
 */
interface Presenter {
    fun start()
    fun onViewCreated()
    fun resume()
    fun pause()
    fun destroy()
}