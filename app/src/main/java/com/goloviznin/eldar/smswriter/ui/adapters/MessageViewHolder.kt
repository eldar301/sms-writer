package com.goloviznin.eldar.smswriter.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.goloviznin.eldar.smswriter.model.Message
import kotlinx.android.synthetic.main.item_message.view.*
import java.text.SimpleDateFormat
import java.util.*

class MessageViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    private val nameTextView = view.textViewName
    private val phoneTextView = view.textViewPhone
    private val dateTextView = view.textViewDate
    private val messageTextView = view.textViewMessage

    fun configure(message: Message) {
        nameTextView.text = message.personInfo?.name
        phoneTextView.text = message.personInfo?.phone
        message.date?.let {
            dateTextView.text = format.format(it)
        }
        messageTextView.text = message.content
    }
}