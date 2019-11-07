package com.goloviznin.eldar.smswriter.presenter

import android.os.AsyncTask
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.goloviznin.eldar.smswriter.application.SmsWriterApplication
import com.goloviznin.eldar.smswriter.domain.interfaces.ContactInfoProvider
import com.goloviznin.eldar.smswriter.view.MessagesListView
import com.goloviznin.eldar.smswriter.domain.interfaces.MessagesProvider
import com.goloviznin.eldar.smswriter.model.Message
import com.goloviznin.eldar.smswriter.model.PersonInfo
import com.goloviznin.eldar.smswriter.util.FilterTask
import javax.inject.Inject

@InjectViewState
class SearchPresenter: MvpPresenter<MessagesListView>(), MessagesProvider.Listener, FilterTask.Listener {

    @Inject
    lateinit var messagesProvider: MessagesProvider

    @Inject
    lateinit var contactInfoProvider: ContactInfoProvider

    private val personMap = hashMapOf<String, PersonInfo?>()

    private var filterTask: FilterTask? = null

    private var messages = emptyArray<Message>()
    private var filteredMessages = emptyArray<Message>()
    private var filteredWord = ""

    init {
        SmsWriterApplication.dataComponent.inject(this)
    }

    fun fetchMessages() {
        messagesProvider.listener = this
        messagesProvider.fetchMessages()
    }

    fun getMessage(index: Int): Message {
        val message = filteredMessages[index]
        var senderInfo = message.personInfo
        message.personInfo?.phone?.let {
            senderInfo = personMap[it] ?: contactInfoProvider.fetchInfo(it)
            personMap[it] = senderInfo
        } ?: return message
        message.personInfo = senderInfo
        return message
    }

    fun filter(word: String) {
        filteredWord = word.toLowerCase()
        filterTask?.cancel(true)
        filterTask = FilterTask()
        filterTask?.listener = this
        filterTask?.execute(Pair(messages, filteredWord))
    }

    override fun onFilterResult(result: Array<Message>) {
        filteredMessages = result
        viewState.update(filteredMessages.count())
    }

    override fun onValue(messages: Array<Message>) {
        this.messages = messages
        filter(filteredWord)
    }

}