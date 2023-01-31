package com.example.examplenotes.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.examplenotes.R
import com.example.examplenotes.presentation.screen.alltasks.AllTasksFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AllTasksFragment>(R.id.container)
            }
    }
}
