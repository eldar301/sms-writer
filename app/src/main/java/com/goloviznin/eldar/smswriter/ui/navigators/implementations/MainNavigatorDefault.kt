package com.goloviznin.eldar.smswriter.ui.navigators.implementations

import com.goloviznin.eldar.smswriter.ui.activities.MainActivity
import com.goloviznin.eldar.smswriter.ui.fragments.ErrorFragment
import com.goloviznin.eldar.smswriter.ui.fragments.SearchFragment
import com.goloviznin.eldar.smswriter.ui.navigators.interfaces.MainNavigator
import kotlinx.android.synthetic.main.activity_main.*

class MainNavigatorDefault: MainNavigator {

    override var activity: MainActivity? = null

    override fun showSearchFragment() {
        activity?.let {
            val fragmentManager = it.supportFragmentManager
            if (fragmentManager.findFragmentById(it.contentView.id) is SearchFragment) {
                return
            }
            fragmentManager
                .beginTransaction()
                .replace(it.contentView.id, SearchFragment())
                .commit()
        }
    }

    override fun showPermissionNotGrantedFragment() {
        activity?.let {
            it.supportFragmentManager
                .beginTransaction()
                .replace(it.contentView.id, ErrorFragment())
                .commit()
        }
    }

}