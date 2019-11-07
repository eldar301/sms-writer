package com.goloviznin.eldar.smswriter.ui.navigators.interfaces

import com.goloviznin.eldar.smswriter.model.Message
import com.goloviznin.eldar.smswriter.ui.fragments.SearchFragment

interface SearchNavigator {
    var fragment: SearchFragment?
    fun show()
    fun filter(word: String)
    fun getSelectedMessages(): List<Message>
}