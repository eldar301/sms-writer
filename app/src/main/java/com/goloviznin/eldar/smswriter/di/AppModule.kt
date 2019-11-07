package com.goloviznin.eldar.smswriter.di

import android.content.Context
import com.goloviznin.eldar.smswriter.domain.implementation.ContactInfoProviderDefault
import com.goloviznin.eldar.smswriter.domain.implementation.MessagesProviderDefault
import com.goloviznin.eldar.smswriter.domain.interfaces.ContactInfoProvider
import com.goloviznin.eldar.smswriter.domain.interfaces.MessagesProvider
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {
    @Provides
    fun messagedProvider(): MessagesProvider = MessagesProviderDefault(context)
    @Provides
    fun contactInfoProvider(): ContactInfoProvider = ContactInfoProviderDefault(context)
}