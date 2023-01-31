package com.example.examplenotes.di.module

import com.example.examplenotes.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector
    fun provideMainActivity(): MainActivity
}
