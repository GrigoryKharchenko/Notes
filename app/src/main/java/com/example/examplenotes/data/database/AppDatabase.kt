package com.example.examplenotes.data.database

import com.example.examplenotes.data.dao.TaskDao

interface AppDatabase {
    fun taskDao(): TaskDao
}
