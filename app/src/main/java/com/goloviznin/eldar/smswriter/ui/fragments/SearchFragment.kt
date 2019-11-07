package com.goloviznin.eldar.smswriter.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView

import com.goloviznin.eldar.smswriter.R
import com.goloviznin.eldar.smswriter.model.Message
import com.goloviznin.eldar.smswriter.ui.navigators.implementations.SearchNavigatorDefault
import com.goloviznin.eldar.smswriter.ui.navigators.interfaces.SearchNavigator
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.io.File
import java.io.FileWriter
import java.net.URLConnection


class SearchFragment: Fragment() {

    var navigator: SearchNavigator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onResume() {
        super.onResume()

        saveButton.show()
        saveButton.isEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigator = SearchNavigatorDefault()
        navigator?.fragment = this
        navigator?.show()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                navigator?.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                return false
            }

        })

        saveButton.setOnClickListener {
            navigator?.getSelectedMessages()?.let {
                saveButton.hide()
                saveButton.isEnabled = false

                GlobalScope.launch(Dispatchers.Main) {
                    val file = share(it)

                    val builder = StrictMode.VmPolicy.Builder()
                    StrictMode.setVmPolicy(builder.build())
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = URLConnection.guessContentTypeFromName(file?.name)
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file?.absolutePath))

                    activity?.startActivity(sharingIntent)
                }
            }
        }
    }

    private suspend fun share(messages: List<Message>): File? = withContext(Dispatchers.IO) {
        val messagesText = messages.map { message ->
            var text = ""
            val senderText = message.personInfo?.let { sender ->
                sender.name ?: sender.phone
            } ?: "Неизвестный"
            text += "Отправитель: $senderText\n"

            message.date?.let { date ->
                text += "Дата: ${SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault()).format(date)}\n"
            }

            message.content?.let { content ->
                text += "Сообщение: $content\n"
            }

            return@map text
        }

        val sharedText = messagesText.joinToString("\n\n")

        val root = File(Environment.getExternalStorageDirectory(), "export")
        if (!root.exists()) {
            root.mkdirs()
        }
        val file = File(root, "temp.txt")
        val writer = FileWriter(file)
        writer.append(sharedText)
        writer.flush()
        writer.close()

        return@withContext file
    }

}
