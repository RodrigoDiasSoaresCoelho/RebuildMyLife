package br.com.jesusc.rebuildmylife.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.jesusc.rebuildmylife.helper.TaskDAO

class TaskViewModelFactory(private val taskDAO: TaskDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(taskDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}