package br.com.jesusc.rebuildmylife.helper

import br.com.jesusc.rebuildmylife.model.Task

class TaskDAO(private val dbHelper: DbHelper) {

    companion object {
        @Volatile
        private var instance: TaskDAO? = null

        fun getInstance(dbHelper: DbHelper): TaskDAO {
            return instance ?: synchronized(this) {
                instance ?: TaskDAO(dbHelper).also { instance = it }
            }
        }
    }

    // Inserir uma tarefa
    fun insert(task: Task): Long {
        return dbHelper.insertTask(task)
    }

    // Atualizar uma tarefa
    fun update(task: Task): Int {
        return dbHelper.updateTask(task)
    }

    // Deletar uma tarefa
    fun delete(taskId: Long): Int {
        return dbHelper.deleteTask(taskId)
    }

    // Recuperar uma tarefa pelo ID
    fun getTaskById(taskId: Long): Task? {
        return dbHelper.getTaskById(taskId)
    }

    // Recuperar todas as tarefas
    fun getAllTasks(): List<Task> {
        return dbHelper.getAllTasks()
    }
}
