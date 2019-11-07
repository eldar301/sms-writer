package com.goloviznin.eldar.smswriter.ui.fragments

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.goloviznin.eldar.smswriter.R
import com.goloviznin.eldar.smswriter.model.Message
import com.goloviznin.eldar.smswriter.model.PersonInfo
import com.goloviznin.eldar.smswriter.presenter.SearchPresenter
import com.goloviznin.eldar.smswriter.ui.adapters.MessageViewHolder
import com.goloviznin.eldar.smswriter.ui.adapters.MessagesRecyclerViewAdapter
import com.goloviznin.eldar.smswriter.ui.decorators.RecyclerViewItemDecoration

import com.goloviznin.eldar.smswriter.view.MessagesListView
import kotlinx.android.synthetic.main.fragment_message_list.*
import java.util.*

class MessagesFragment: MvpAppCompatFragment(), MessagesListView {

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    private var messagesCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewSearch.adapter = MessagesRecyclerViewAdapter(emptyList())
        val marginRect = Rect(16, 24, 16, 10)
        recyclerViewSearch.addItemDecoration(RecyclerViewItemDecoration(marginRect))

        presenter.fetchMessages()
    }

    override fun update(messagesCount: Int) {
        activity?.runOnUiThread {
            this.messagesCount = messagesCount
            recyclerViewSearch.adapter = object: RecyclerView.Adapter<MessageViewHolder>() {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
                    return MessageViewHolder(view)
                }

                override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
                    val message = presenter.getMessage(position)
                    holder.configure(message)
                }

                override fun getItemCount(): Int = messagesCount

            }
            recyclerViewSearch.invalidate()
            textViewEmptyMessages.visibility = if (messagesCount == 0) View.VISIBLE else View.GONE
        }
    }

    fun filter(word: String) {
        presenter.filter(word)
    }

    fun getCurrentMessages(): List<Message> {
        val messages = mutableListOf<Message>()
        for (index in 0 until messagesCount) {
            messages.add(presenter.getMessage(index))
        }
        return messages
    }

}
