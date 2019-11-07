package com.goloviznin.eldar.smswriter.ui.navigators.interfaces

import com.goloviznin.eldar.smswriter.ui.activities.MainActivity

interface MainNavigator {
    var activity: MainActivity?
    fun showSearchFragment()
    fun showPermissionNotGrantedFragment()
}