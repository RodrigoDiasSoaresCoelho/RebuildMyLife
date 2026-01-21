package br.com.jesusc.rebuildmylife.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jesusc.rebuildmylife.helper.TaskDAO
import br.com.jesusc.rebuildmylife.model.Task
import br.com.jesusc.rebuildmylife.model.UiDate
import br.com.jesusc.rebuildmylife.util.CallbackTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.sortedWith

class TaskViewModel(private val taskDAO: TaskDAO) : ViewModel() {

    // LiveData para observar as Tasks
    private val _tasks = MutableLiveData<MutableList<Task>>()
    val tasks: LiveData<MutableList<Task>> get() = _tasks


    // Função para inserir uma tarefa
    fun insertTask(task: Task) = viewModelScope.launch {
        taskDAO.insert(task)
    }

    // Função para atualizar uma tarefa
    fun updateTask(task: Task) = viewModelScope.launch {
        taskDAO.update(task)
    }

    // Função para deletar uma tarefa
    fun deleteTask(task: Task) = viewModelScope.launch {
        taskDAO.delete(task.id)
    }

    fun loadTasks(uiDate: UiDate) {
        viewModelScope.launch {
            val taskList = withContext(Dispatchers.IO) {
                taskDAO.getTasksByDate(uiDate)
//                taskDAO.getAllTasks()
            }

            val sortedList = taskList.sortedWith(
                compareBy<Task> { it.checked }
                    .thenBy { it.enumPriority.value }
            )
            _tasks.postValue(sortedList.toMutableList())
        }
    }

    fun taskChecked(callbackTask: CallbackTask){
        _tasks.value = _tasks.value?.sortedWith(
            compareBy<Task> { it.checked }
                .thenBy { it.enumPriority.value }
        ) as MutableList<Task>?
        callbackTask.notifyDataSetChanged()
    }
}


