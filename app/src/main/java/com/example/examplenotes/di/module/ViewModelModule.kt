package com.example.examplenotes.di.module

import androidx.lifecycle.ViewModel
import com.example.examplenotes.di.ViewModelKey
import com.example.examplenotes.presentation.screen.addtask.AddTaskViewModel
import com.example.examplenotes.presentation.screen.alltasks.AllTasksViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AllTasksViewModel::class)
    fun bindAllTasksViewModel(viewModel: AllTasksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTaskViewModel::class)
    fun bindAddTaskViewModel(viewModel: AddTaskViewModel): ViewModel
}
