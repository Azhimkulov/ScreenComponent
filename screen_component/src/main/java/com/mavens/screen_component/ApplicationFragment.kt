package com.mavens.screen_component

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mavens.screen_component.activity.ApplicationActivity
import com.mavens.screen_component.presenter.Presenter

/**
 * Created by azamat  on 10/19/20.
 */
abstract class ApplicationFragment  : Fragment(),
    ApplicationLoadDataView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setThemeBackgroundColor()
        providePresenter().onViewCreated()
    }

    override fun onStart() {
        super.onStart()
        providePresenter().start()
    }

    override fun onResume() {
        super.onResume()
        providePresenter().resume()
    }

    override fun onPause() {
        super.onPause()
        providePresenter().resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        providePresenter().destroy()
    }

    override fun context(): Context? {
        return activity
    }

    override fun isFragmentVisible() = this.isVisible

    private fun setThemeBackgroundColor() {
        context?.let {
            val colorId = provideBackgroundColor()
            view?.setBackgroundColor(ContextCompat.getColor(it, colorId))
        }
    }

    protected fun setupActionBar(customView: View) {
        activity?.let {
            if (it is ApplicationActivity) {
                it.setupActionBar(customView)
            }
        }
    }

    protected fun showActionButton() {
        activity?.let {
            if (it is ApplicationActivity) {
                it.showActionBar()
            }
        }
    }

    protected fun hideActionButton() {
        activity?.let {
            if (it is ApplicationActivity) {
                it.hideActionBar()
            }
        }
    }

    protected abstract fun providePresenter(): Presenter

    protected abstract fun provideBackgroundColor(): Int

    protected abstract fun <C> getComponent(componentType: Class<C>): C
}