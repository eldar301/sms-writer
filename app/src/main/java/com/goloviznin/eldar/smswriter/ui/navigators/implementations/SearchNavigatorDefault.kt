package com.goloviznin.eldar.smswriter.ui.navigators.implementations

import com.goloviznin.eldar.smswriter.model.Message
import com.goloviznin.eldar.smswriter.ui.fragments.MessagesFragment
import com.goloviznin.eldar.smswriter.ui.fragments.SearchFragment
import com.goloviznin.eldar.smswriter.ui.navigators.interfaces.SearchNavigator
import kotlinx.android.synthetic.main.fragment_search.*

class SearchNavigatorDefault: SearchNavigator {

    override var fragment: SearchFragment? = null

    override fun show() {
        fragment?.let {
            it.childFragmentManager
                .beginTransaction()
                .replace(it.searchContentView.id, MessagesFragment())
                .commit()
        }
    }

    override fun filter(word: String) {
        fragment?.let {
            val childFragment = it.childFragmentManager.findFragmentById(it.searchContentView.id)
            if (childFragment is MessagesFragment) {
                childFragment.filter(word)
            }
        }
    }

    override fun getSelectedMessages(): List<Message> {
        fragment?.let {
            val childFragment = it.childFragmentManager.findFragmentById(it.searchContentView.id)
            if (childFragment is MessagesFragment) {
                return childFragment.getCurrentMessages()
            }
        }
        return emptyList()
    }

}