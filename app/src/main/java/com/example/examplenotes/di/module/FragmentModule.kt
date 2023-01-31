package com.example.examplenotes.di.module

import com.example.examplenotes.presentation.screen.addtask.AddTaskFragment
import com.example.examplenotes.presentation.screen.alltasks.AllTasksFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun bindAllTasksFragment(): AllTasksFragment

    @ContributesAndroidInjector
    fun bindAddTaskFragment(): AddTaskFragment
}
