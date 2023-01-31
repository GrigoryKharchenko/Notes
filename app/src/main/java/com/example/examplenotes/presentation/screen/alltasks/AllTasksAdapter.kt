package com.example.examplenotes.presentation.screen.alltasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examplenotes.databinding.ItemTaskBinding
import com.example.examplenotes.presentation.model.TaskUiModel

class AllTasksAdapter(
    val onItemClick: (TaskUiModel) -> Unit,
    val onCheckClick: (Int, Boolean) -> Unit
) : ListAdapter<TaskUiModel, AllTasksViewHolder>(AllTasksDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTasksViewHolder =
        AllTasksViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AllTasksViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClick, onCheckClick)
}

class AllTasksViewHolder(private val binding: ItemTaskBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        taskUiModel: TaskUiModel,
        onItemClick: (TaskUiModel) -> Unit,
        onCheckClick: (Int, Boolean) -> Unit,
    ) {
        with(binding) {
            cbComplete.isChecked = taskUiModel.isComplete
            tvTitle.text = taskUiModel.title
            tvDescription.text = taskUiModel.description
            cbComplete.setOnClickListener {
                onCheckClick(taskUiModel.id, cbComplete.isChecked)
            }
            root.setOnClickListener { onItemClick(taskUiModel) }
        }
    }
}

class AllTasksDiffUtil : DiffUtil.ItemCallback<TaskUiModel>() {
    override fun areItemsTheSame(oldItem: TaskUiModel, newItem: TaskUiModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TaskUiModel, newItem: TaskUiModel): Boolean =
        oldItem == newItem
}
