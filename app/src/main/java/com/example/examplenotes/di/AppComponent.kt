package com.example.examplenotes.di

import android.content.Context
import com.example.examplenotes.TasksApp
import com.example.examplenotes.di.module.ActivityModule
import com.example.examplenotes.di.module.DatabaseModule
import com.example.examplenotes.di.module.DispatcherModule
import com.example.examplenotes.di.module.FragmentModule
import com.example.examplenotes.di.module.RepositoryModule
import com.example.examplenotes.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        DispatcherModule::class
    ]
)
interface AppComponent : AndroidInjector<TasksApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
