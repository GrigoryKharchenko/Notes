package com.example.examplenotes.di.module

import android.content.Context
import com.example.examplenotes.data.database.AppDatabase
import com.example.examplenotes.data.database.AppRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase = AppRoomDatabase.buildDatabase(context)

    @Provides
    fun provideNoteDao(appDatabase: AppDatabase) = appDatabase.taskDao()
}
