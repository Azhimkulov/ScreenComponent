package com.mavens.screen_component.activity

import android.app.Application
import android.os.Handler
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment


/**
 * Created by azamat  on 2020-05-21.
 */
abstract class ApplicationActivity : AppCompatActivity() {
    private enum class FragmentAction {
        ADD,
        REPLACE
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun setFragment(
        containerViewId: Int,
        fragment: Fragment,
        fragmentAction: FragmentAction
    ) {
        Handler().post { setFragmentAux(containerViewId, fragment, fragmentAction) }
    }

    private fun addFragment(containerViewId: Int, fragment: Fragment) {
        setFragment(containerViewId, fragment, FragmentAction.ADD)
    }

    protected fun addFragment(containerView: View, fragment: Fragment) {
        addFragment(containerView.id, fragment)
    }

    private fun replaceFragment(containerViewId: Int, fragment: Fragment) {
        setFragment(containerViewId, fragment, FragmentAction.REPLACE)
    }

    protected fun replaceFragment(containerView: View, fragment: Fragment) {
        replaceFragment(containerView.id, fragment)
    }

    private fun setFragmentAux(
        containerViewId: Int,
        fragment: Fragment,
        fragmentAction: FragmentAction
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (fragmentAction) {
            FragmentAction.REPLACE -> fragmentTransaction.replace(
                containerViewId,
                fragment
            )
            FragmentAction.ADD -> {
                fragmentTransaction.replace(containerViewId, fragment)
                fragmentTransaction.addToBackStack(null)
            }
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun setupActionBar(customView: View, elevation: Float? = null) {
        showActionBar()

        supportActionBar?.let {
            it.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            it.setCustomView(
                customView,
                ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER_VERTICAL or Gravity.END
                )
            )

            elevation?.let { value ->
                it.elevation = value
            }

            val parent = it.customView.parent as Toolbar
            parent.setPadding(0, 0, 0, 0)
            parent.setContentInsetsAbsolute(0, 0)
        }
    }

    fun showActionBar() {
        supportActionBar?.show()
    }

    fun hideActionBar() {
        supportActionBar?.hide()
    }
}