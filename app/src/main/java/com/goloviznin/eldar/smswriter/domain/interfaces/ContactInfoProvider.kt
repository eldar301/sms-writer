package com.goloviznin.eldar.smswriter.domain.interfaces

import com.goloviznin.eldar.smswriter.model.PersonInfo

interface ContactInfoProvider {
    fun fetchInfo(phone: String): PersonInfo?
}