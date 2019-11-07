package com.goloviznin.eldar.smswriter.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = SingleStateStrategy::class)
interface MessagesListView: MvpView {
    fun update(messagesCount: Int)
}