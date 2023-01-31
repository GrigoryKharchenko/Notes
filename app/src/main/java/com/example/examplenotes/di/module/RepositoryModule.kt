package com.example.examplenotes.di.module

import com.example.examplenotes.data.repository.TaskRepository
import com.example.examplenotes.data.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindTaskRepository(repositoryImpl: TaskRepositoryImpl): TaskRepository
}
