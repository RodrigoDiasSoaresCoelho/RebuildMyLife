package br.com.jesusc.rebuildmylife.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jesusc.rebuildmylife.helper.TaskDAO
import br.com.jesusc.rebuildmylife.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(private val taskDAO: TaskDAO) : ViewModel() {

    // LiveData para observar as Tasks
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks


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

    fun loadTasks() {
        viewModelScope.launch {
            // Executar em uma thread de background
            val taskList = withContext(Dispatchers.IO) {
                taskDAO.getAllTasks()
            }
            _tasks.postValue(taskList)
        }
    }
}


