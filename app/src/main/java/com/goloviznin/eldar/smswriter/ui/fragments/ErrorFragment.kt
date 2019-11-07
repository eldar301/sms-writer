package com.goloviznin.eldar.smswriter.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.goloviznin.eldar.smswriter.R
import androidx.core.content.ContextCompat.startActivity
import android.net.Uri.fromParts
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_error.view.*


class ErrorFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_error, container, false)

        view.settingsButton.setOnClickListener {
            activity?.let {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", it.packageName, null)
                intent.data = uri
                it.startActivity(intent)
            }
        }

        return  view
    }


}
