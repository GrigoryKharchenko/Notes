package com.example.examplenotes

import com.example.examplenotes.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TasksApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent
            .builder()
            .context(this)
            .build()
}

