package com.goloviznin.eldar.smswriter.domain.interfaces

import com.goloviznin.eldar.smswriter.model.Message

interface MessagesProvider {
    interface Listener {
        fun onValue(messages: Array<Message>)
    }

    var listener: Listener?

    fun fetchMessages()
}