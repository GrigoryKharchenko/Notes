package com.example.examplenotes.presentation.screen.addtask

import com.example.examplenotes.R

data class AddTuskUiState(
    val toolbarTitle: Int = R.string.add_task_title,
    val titleTusk: String = "",
    val descriptionTusk: String = "",
    val isVisibleDeleteButton: Boolean = false
)

data class Errors(
    val errorTitle: Int? = null,
    val errorDescription: Int? = null
)
