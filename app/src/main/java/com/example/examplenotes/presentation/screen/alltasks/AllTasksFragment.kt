package com.example.examplenotes.presentation.screen.alltasks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.examplenotes.R
import com.example.examplenotes.databinding.FragmentAllTasksBinding
import com.example.examplenotes.di.ViewModelFactory
import com.example.examplenotes.extension.addFragment
import com.example.examplenotes.extension.launchWhenStarted
import com.example.examplenotes.extension.setStatusBarColor
import com.example.examplenotes.presentation.screen.addtask.AddTaskFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AllTasksFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: FragmentAllTasksBinding? = null
    private val binding get() = _binding!!

    private val adapter: AllTasksAdapter = AllTasksAdapter(
        onItemClick = { taskModel ->
            startAddTaskFragment(taskModel.id)
        },
        onCheckClick = { id, isComplete ->
            viewModel.updateTaskIsComplete(id, isComplete)
        }
    )

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[AllTasksViewModel::class.java]
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllTasksBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
        setStatusBarColor(R.color.background_status_bar_color)
    }

    private fun initUi() {
        with(binding) {
            rvTasks.adapter = adapter
            tvCompleteTasks.text = getString(R.string.all_tasks_complete_tasks, INIT_COMPLETE_TASKS.toString())
            fabAddTusk.setOnClickListener {
                addFragment<AddTaskFragment>(R.id.container)
            }
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            allNotesFlow.onEach { taskModels ->
                adapter.submitList(taskModels)
                calculateTask(taskModels)
            }.launchWhenStarted(lifecycleScope, viewLifecycleOwner.lifecycle)
            counterTaskFlow.onEach { completeTask ->
                binding.tvCompleteTasks.text = getString(R.string.all_tasks_complete_tasks, completeTask.toString())
            }.launchWhenStarted(lifecycleScope, viewLifecycleOwner.lifecycle)
        }
    }

    private fun startAddTaskFragment(id: Int) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(AddTaskFragment.NAME_BACK_STACK)
            .replace(R.id.container, AddTaskFragment.newInstance(id))
            .commit()
    }

    override fun onDestroyView() {
        binding.rvTasks.adapter = null
        super.onDestroyView()
        _binding = null

    }

    companion object {
        private const val INIT_COMPLETE_TASKS = 0
    }
}
