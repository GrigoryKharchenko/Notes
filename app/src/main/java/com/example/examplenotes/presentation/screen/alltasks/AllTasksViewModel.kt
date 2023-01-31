package com.example.examplenotes.presentation.screen.alltasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplenotes.data.mapper.toTaskUiModel
import com.example.examplenotes.di.IoDispatcher
import com.example.examplenotes.data.repository.TaskRepository
import com.example.examplenotes.presentation.model.TaskUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllTasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _allNotesFlow = MutableStateFlow<List<TaskUiModel>>(emptyList())
    val allNotesFlow = _allNotesFlow.asStateFlow()

    private val _counterTaskFlow = MutableStateFlow(0)
    val counterTaskFlow = _counterTaskFlow.asStateFlow()

    init {
        subscribeTask()
    }

    fun updateTaskIsComplete(id: Int, isComplete: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            taskRepository.updateTaskCounter(id, isComplete)
        }
    }

    fun calculateTask(taskModel: List<TaskUiModel>) {
        viewModelScope.launch(ioDispatcher) {
            var completeTask = 0
            taskModel.forEach { task ->
                if (task.isComplete) {
                    completeTask++
                }
            }
            _counterTaskFlow.emit(completeTask)
        }
    }

    private fun subscribeTask() {
        viewModelScope.launch(ioDispatcher) {
            taskRepository.subscribeToReceive().map { tasksEntity ->
                tasksEntity.toTaskUiModel()
            }.onEach { tasksModel ->
                _allNotesFlow.emit(tasksModel)
            }.launchIn(viewModelScope)
        }
    }
}
