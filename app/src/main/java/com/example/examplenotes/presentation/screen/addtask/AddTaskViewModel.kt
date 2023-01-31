package com.example.examplenotes.presentation.screen.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplenotes.R
import com.example.examplenotes.di.IoDispatcher
import com.example.examplenotes.data.repository.TaskRepository
import com.example.examplenotes.presentation.model.TaskUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(AddTuskUiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _errorFlow = MutableStateFlow(Errors())
    val errorFlow = _errorFlow.asStateFlow()

    private val _transactionFragmentFlow = MutableSharedFlow<Unit>()
    val transactionFragmentFlow = _transactionFragmentFlow.asSharedFlow()

    fun saveOrUpdateTask(id: Int?, title: String, description: String) {
        viewModelScope.launch {
            if (id == null) {
                saveTask(title, description)
            } else {
                updateTask(id, title, description)
            }
        }
    }

    fun updateUiState(title: String, description: String) {
        viewModelScope.launch {
            _uiStateFlow.update {
                it.copy(
                    titleTusk = title,
                    descriptionTusk = description
                )
            }
        }
    }

    fun getTask(id: Int) {
        viewModelScope.launch {
            val task = taskRepository.selectedById(id)
            _uiStateFlow.update {
                it.copy(
                    toolbarTitle = R.string.edit_task_title,
                    titleTusk = task.title,
                    descriptionTusk = task.description,
                    isVisibleDeleteButton = true
                )
            }
            updateUiState(task.title, task.description)
        }
    }


    fun deleteTask(noteId: Int) {
        viewModelScope.launch {
            taskRepository.deleteTask(noteId)
            _transactionFragmentFlow.emit(Unit)
        }
    }

    fun hideTitleError() {
        viewModelScope.launch {
            _errorFlow.update {
                it.copy(errorTitle = null)
            }
        }
    }

    fun hideDescriptionError() {
        viewModelScope.launch {
            _errorFlow.update {
                it.copy(errorDescription = null)
            }
        }
    }

    private fun saveTask(title: String, description: String) {
        viewModelScope.launch(ioDispatcher) {
            val taskModel = TaskUiModel(title = title, description = description, isComplete = false)
            if (title.isNotEmpty() && description.isNotEmpty()) {
                taskRepository.insertTask(taskModel)
                _transactionFragmentFlow.emit(Unit)
            } else {
                _errorFlow.emit(
                    Errors(
                        if (title.isEmpty()) R.string.error_title else null,
                        if (description.isEmpty()) R.string.error_description else null,
                    )
                )
            }
        }
    }

    private fun updateTask(id: Int, title: String, description: String) {
        viewModelScope.launch(ioDispatcher) {
            taskRepository.updateTask(id, title, description)
            _transactionFragmentFlow.emit(Unit)
        }
    }
}
