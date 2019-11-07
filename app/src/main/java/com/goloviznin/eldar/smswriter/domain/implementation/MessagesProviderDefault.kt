package com.goloviznin.eldar.smswriter.domain.implementation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.provider.Telephony
import androidx.core.content.ContextCompat
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.goloviznin.eldar.smswriter.domain.interfaces.MessagesProvider
import com.goloviznin.eldar.smswriter.model.Message
import com.goloviznin.eldar.smswriter.model.PersonInfo
import java.util.*

class MessagesProviderDefault(private val context: Context): MessagesProvider {

    override var listener: MessagesProvider.Listener? = null

    override fun fetchMessages() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            AsyncTask.execute {
                val cursor = context.contentResolver.query(
                    Uri.parse("content://sms/inbox"),
                    arrayOf(
                        Telephony.TextBasedSmsColumns.PERSON,
                        Telephony.TextBasedSmsColumns.ADDRESS,
                        Telephony.TextBasedSmsColumns.DATE,
                        Telephony.TextBasedSmsColumns.BODY
                    ),
                    null,
                    null,
                    null)

                val messages = mutableListOf<Message>()

                cursor?.let {
                    if (it.moveToFirst()) {
                        val nameIndex = cursor.getColumnIndex(Telephony.TextBasedSmsColumns.PERSON)
                        val phoneIndex = cursor.getColumnIndex(Telephony.TextBasedSmsColumns.ADDRESS)
                        val dateIndex = cursor.getColumnIndex(Telephony.TextBasedSmsColumns.DATE)
                        val bodyIndex = cursor.getColumnIndex(Telephony.TextBasedSmsColumns.BODY)

                        do {
                            val personInfo = PersonInfo(
                                cursor.getStringOrNull(nameIndex),
                                cursor.getStringOrNull(phoneIndex)
                            )
                            var date: Date? = null
                            cursor.getLongOrNull(dateIndex)?.let { long ->
                                date = Date(long)
                            }
                            val message = Message(
                                personInfo,
                                date,
                                cursor.getStringOrNull(bodyIndex)
                            )

                            messages.add(message)
                        } while (cursor.moveToNext())
                    }
                }

                cursor?.close()

                listener?.onValue(messages.toTypedArray())
            }
        } else {
            listener?.onValue(emptyArray())
        }
    }

}