package br.com.jesusc.rebuildmylife.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.jesusc.rebuildmylife.model.Task

@Dao
interface TaskDao {

    // Inserir uma nova tarefa (ID auto-incrementado)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    // Atualizar uma tarefa existente
    @Update
    suspend fun update(task: Task)

    // Buscar todas as tarefas
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    // Buscar uma tarefa pelo ID
    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): Task?

    // Deletar uma tarefa específica
    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTaskById(id: Long)

    // Deletar todas as tarefas
    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}