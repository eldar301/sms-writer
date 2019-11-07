package com.goloviznin.eldar.smswriter.application

import android.app.Application
import com.goloviznin.eldar.smswriter.di.AppComponent
import com.goloviznin.eldar.smswriter.di.AppModule
import com.goloviznin.eldar.smswriter.di.DaggerAppComponent

class SmsWriterApplication: Application() {

    companion object {
        lateinit var dataComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        dataComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(applicationContext))
            .build()
    }

}