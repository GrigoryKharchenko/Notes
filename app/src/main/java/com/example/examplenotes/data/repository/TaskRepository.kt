package com.example.examplenotes.data.repository

import com.example.examplenotes.data.entity.TaskEntity
import com.example.examplenotes.presentation.model.TaskUiModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun subscribeToReceive(): Flow<List<TaskEntity>>
    suspend fun insertTask(taskModel: TaskUiModel)
    suspend fun deleteTask(taskId: Int)
    suspend fun selectedById(taskId: Int): TaskUiModel
    suspend fun updateTask(id: Int, title: String, description: String)
    suspend fun updateTaskCounter(id: Int, icComplete: Boolean)
}
