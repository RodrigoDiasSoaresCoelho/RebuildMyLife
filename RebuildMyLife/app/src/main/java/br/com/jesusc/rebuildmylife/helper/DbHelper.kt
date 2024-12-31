package br.com.jesusc.rebuildmylife.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import br.com.jesusc.rebuildmylife.enums.EnumPriority
import br.com.jesusc.rebuildmylife.model.Notification
import br.com.jesusc.rebuildmylife.model.Schedule
import br.com.jesusc.rebuildmylife.model.Task
import com.google.gson.Gson

class DbHelper(context: Context?) : SQLiteOpenHelper(context, NOME_DB, null, VERSION) {

    companion object {
        private const val VERSION = 1
        private const val NOME_DB = "DB_TODO"
        const val TABLE_TASK = "tasks"

        // Instância única do DbHelper
        @Volatile
        private var instance: DbHelper? = null

        // Método para obter a instância única
        fun getInstance(context: Context): DbHelper {
            return instance ?: synchronized(this) {
                instance ?: DbHelper(context.applicationContext).also { instance = it }
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Criação da tabela para a classe Task
        val sql = """
            CREATE TABLE IF NOT EXISTS $TABLE_TASK (
                id INTEGER PRIMARY KEY AUTOINCREMENT, 
                title TEXT NOT NULL, 
                description TEXT NOT NULL, 
                enumPriority TEXT NOT NULL, 
                hour TEXT NOT NULL, 
                repeat TEXT NOT NULL, 
                notification TEXT NOT NULL
            )
        """.trimIndent()
        try {
            db.execSQL(sql)
            Log.i("INFO DB", "SUCESSO ao criar a tabela")
        } catch (e: Exception) {
            Log.i("INFO DB", "Erro ao criar a tabela: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Para simplificação, recriamos a tabela no caso de atualização
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        onCreate(db)
    }

    // Inserir uma tarefa
    fun insertTask(task: Task): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", task.title)
            put("description", task.description)
            put("enumPriority", task.enumPriority.name) // Salvamos como String
            put("hour", task.hour)
            put("repeat", task.repeat.toJson()) // Convertido para JSON
            put("notification", task.notification.toJson()) // Convertido para JSON
        }
        return db.insert(TABLE_TASK, null, values)
    }

    // Atualizar uma tarefa
    fun updateTask(task: Task): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", task.title)
            put("description", task.description)
            put("enumPriority", task.enumPriority.name)
            put("hour", task.hour)
            put("repeat", task.repeat.toJson())
            put("notification", task.notification.toJson())
        }
        return db.update(TABLE_TASK, values, "id = ?", arrayOf(task.id.toString()))
    }

    // Deletar uma tarefa
    fun deleteTask(id: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_TASK, "id = ?", arrayOf(id.toString()))
    }

    // Recuperar uma tarefa pelo ID
    fun getTaskById(id: Long): Task? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TASK WHERE id = ?", arrayOf(id.toString()))
        return if (cursor.moveToFirst()) {
            val task = cursor.toTask()
            cursor.close()
            task
        } else {
            cursor.close()
            null
        }
    }

    // Recuperar todas as tarefas
    fun getAllTasks(): List<Task> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TASK", null)
        val tasks = mutableListOf<Task>()
        if (cursor.moveToFirst()) {
            do {
                tasks.add(cursor.toTask())
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tasks
    }
}

// Métodos auxiliares para serialização e deserialização

// Método de extensão para converter qualquer objeto para JSON
fun Any.toJson(): String {
    return Gson().toJson(this)
}

// Método de extensão para converter um Cursor em uma instância de Task
fun Cursor.toTask(): Task {
    val id = getLong(getColumnIndexOrThrow("id"))
    val title = getString(getColumnIndexOrThrow("title"))
    val description = getString(getColumnIndexOrThrow("description"))
    val enumPriority = EnumPriority.valueOf(getString(getColumnIndexOrThrow("enumPriority")))
    val hour = getString(getColumnIndexOrThrow("hour"))
    val repeat = Schedule.fromJson(getString(getColumnIndexOrThrow("repeat")))
    val notification = Notification.fromJson(getString(getColumnIndexOrThrow("notification")))

    return Task(id, title, description, enumPriority, hour, repeat, notification)
}

// Métodos de serialização e desserialização para Schedule
fun Schedule.Companion.fromJson(json: String): Schedule {
    return Gson().fromJson(json, Schedule::class.java)
}

// Métodos de serialização e desserialização para Notification
fun Notification.Companion.fromJson(json: String): Notification {
    return Gson().fromJson(json, Notification::class.java)
}
