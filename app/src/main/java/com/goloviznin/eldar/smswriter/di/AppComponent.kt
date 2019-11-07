package com.goloviznin.eldar.smswriter.di

import com.goloviznin.eldar.smswriter.presenter.SearchPresenter
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(presenter: SearchPresenter)
}