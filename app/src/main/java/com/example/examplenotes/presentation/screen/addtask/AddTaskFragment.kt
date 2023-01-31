package com.example.examplenotes.presentation.screen.addtask

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.examplenotes.R
import com.example.examplenotes.databinding.FragmentAddTaskBinding
import com.example.examplenotes.di.ViewModelFactory
import com.example.examplenotes.extension.launchWhenStarted
import com.example.examplenotes.extension.setStatusBarColor
import com.example.examplenotes.extension.showDialog
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AddTaskFragment : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[AddTaskViewModel::class.java]
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
        _binding = FragmentAddTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeViewModel()
        setStatusBarColor(R.color.background_status_bar_color)
        if (savedInstanceState == null)
            getArgs()?.let { id -> viewModel.getTask(id) }
    }

    private fun getArgs(): Int? {
        return try {
            arguments?.getInt(KEY_TASK)
        } catch (e: NumberFormatException) {
            null
        }
    }

    private fun initUi() {
        with(binding) {
            toolBar.setNavigationOnClickListener { goBack() }
            etTitleTask.setOnFocusChangeListener { _, _ ->
                viewModel.hideTitleError()
            }
            etDescriptionTask.setOnFocusChangeListener { _, _ ->
                viewModel.hideDescriptionError()
            }
            btnSave.setOnClickListener {
                viewModel.saveOrUpdateTask(
                    getArgs(),
                    etTitleTask.text.toString(),
                    etDescriptionTask.text.toString()
                )
            }
            btnDelete.setOnClickListener {
                showDialog(
                    title = R.string.edit_task_dialog_title,
                    description = R.string.edit_task_dialog_description,
                    positiveButton = R.string.edit_task_dialog_ok,
                    negativeButton = R.string.edit_task_dialog_cancel,
                    onClickPositiveButton = {
                        getArgs()?.let { id -> viewModel.deleteTask(id) }
                    }
                )
            }
        }
    }

    private fun subscribeViewModel() {
        with(viewModel) {
            uiStateFlow.onEach { tuskUiState ->
                handleUiState(tuskUiState)
            }.launchWhenStarted(lifecycleScope, viewLifecycleOwner.lifecycle)
            errorFlow.onEach { errors ->
                setErrors(errors)
            }.launchWhenStarted(lifecycleScope, viewLifecycleOwner.lifecycle)
            transactionFragmentFlow.onEach {
                goBack()
            }.launchWhenStarted(lifecycleScope, viewLifecycleOwner.lifecycle)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.updateUiState(binding.etTitleTask.text.toString(), binding.etDescriptionTask.text.toString())
    }

    private fun setErrors(errors: Errors) {
        with(binding) {
            tilTitleTask.error = errors.errorTitle?.let { getString(it) }
            etDescriptionTask.error = errors.errorDescription?.let { getString(it) }
        }
    }

    private fun handleUiState(addTuskUiState: AddTuskUiState) {
        with(binding) {
            toolBar.title = getString(addTuskUiState.toolbarTitle)
            etTitleTask.setText(addTuskUiState.titleTusk)
            etDescriptionTask.setText(addTuskUiState.descriptionTusk)
            btnDelete.isVisible = addTuskUiState.isVisibleDeleteButton
        }
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(id: Int) = AddTaskFragment().apply {
            arguments = bundleOf(KEY_TASK to id)
        }

        const val NAME_BACK_STACK = "AddTaskFragment"
        private const val KEY_TASK = "taskId"
    }
}
