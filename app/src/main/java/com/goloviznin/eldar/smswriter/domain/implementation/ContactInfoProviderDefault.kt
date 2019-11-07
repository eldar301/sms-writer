package com.goloviznin.eldar.smswriter.domain.implementation

import android.Manifest
import com.goloviznin.eldar.smswriter.domain.interfaces.ContactInfoProvider
import com.goloviznin.eldar.smswriter.model.PersonInfo
import android.provider.ContactsContract.PhoneLookup
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat


class ContactInfoProviderDefault(private val context: Context): ContactInfoProvider {

    override fun fetchInfo(phone: String): PersonInfo? {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            return null
        }

        val cursor = context.contentResolver.query(
            Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone)),
            arrayOf(PhoneLookup.DISPLAY_NAME),
            null,
            null,
            null
        )
        var name: String? = null
        cursor?.let {
            if (it.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME))
            }
        } ?: return PersonInfo(null, phone)

        cursor.close()

        return PersonInfo(name, phone)
    }

}