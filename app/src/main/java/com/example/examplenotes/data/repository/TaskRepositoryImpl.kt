package com.example.examplenotes.data.repository

import com.example.examplenotes.data.dao.TaskDao
import com.example.examplenotes.data.entity.TaskEntity
import com.example.examplenotes.data.mapper.mapToTaskUiModel
import com.example.examplenotes.presentation.model.TaskUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override fun subscribeToReceive(): Flow<List<TaskEntity>> = taskDao.subscribeToReceive()

    override suspend fun insertTask(taskModel: TaskUiModel) {
        taskDao.insertTask(
            TaskEntity(
                id = taskModel.id,
                title = taskModel.title,
                description = taskModel.description,
                isComplete = taskModel.isComplete
            )
        )
    }

    override suspend fun deleteTask(taskId: Int) =
        taskDao.deleteTask(taskId)


    override suspend fun selectedById(taskId: Int): TaskUiModel =
        taskDao.selectById(taskId).mapToTaskUiModel()

    override suspend fun updateTask(id: Int, title: String, description: String) =
        taskDao.updateTask(id, title, description)


    override suspend fun updateTaskCounter(id: Int, isComplete: Boolean) =
        taskDao.updateTaskCounter(id, isComplete)
}
