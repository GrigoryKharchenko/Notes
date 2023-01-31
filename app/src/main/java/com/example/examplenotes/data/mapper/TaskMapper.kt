package com.example.examplenotes.data.mapper

import com.example.examplenotes.data.entity.TaskEntity
import com.example.examplenotes.presentation.model.TaskUiModel

fun List<TaskEntity>.toTaskUiModel() = this.map { taskEntity ->
    taskEntity.mapToTaskUiModel()
}

fun TaskEntity.mapToTaskUiModel(): TaskUiModel =
    TaskUiModel(
        id = id,
        title = title,
        description = description,
        isComplete = isComplete
    )
