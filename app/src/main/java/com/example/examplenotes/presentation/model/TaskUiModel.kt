package com.example.examplenotes.presentation.model

private const val INIT_ID = -1

data class TaskUiModel(
    val id: Int = INIT_ID,
    val title: String,
    val description: String,
    val isComplete: Boolean,
)
