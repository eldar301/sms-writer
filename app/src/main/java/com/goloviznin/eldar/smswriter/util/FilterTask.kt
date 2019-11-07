package com.goloviznin.eldar.smswriter.util

import android.os.AsyncTask
import com.goloviznin.eldar.smswriter.model.Message

class FilterTask: AsyncTask<Pair<Array<Message>, String>, Void, Array<Message>>() {

    interface Listener {
        fun onFilterResult(result: Array<Message>)
    }

    var listener: Listener? = null

    override fun doInBackground(vararg params: Pair<Array<Message>, String>?): Array<Message> {
        val param = params.first()
        param?.let {
            val messages = it.first
            val word = it.second
            if (!word.isEmpty()) {
                val filteredMessages = emptyList<Message>().toMutableList()
                for (message in messages) {
                    if (isCancelled) {
                        return emptyArray()
                    }
                    if (message.content?.toLowerCase()?.contains(word) == true) {
                        filteredMessages.add(message)
                    }
                }
                return filteredMessages.toTypedArray()
            } else {
                return messages
            }
        } ?: return emptyArray()
    }

    override fun onPostExecute(result: Array<Message>?) {
        result?.let {
            listener?.onFilterResult(it)
        }
    }

}