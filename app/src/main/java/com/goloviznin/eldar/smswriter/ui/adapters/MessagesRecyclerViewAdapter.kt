package com.goloviznin.eldar.smswriter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goloviznin.eldar.smswriter.R
import com.goloviznin.eldar.smswriter.model.Message

class MessagesRecyclerViewAdapter(
    private val messages: List<Message>
): RecyclerView.Adapter<MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val contact = messages[position]
        holder.configure(contact)
    }

    override fun getItemCount(): Int = messages.size
}