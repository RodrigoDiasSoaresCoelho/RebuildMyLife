package br.com.jesusc.rebuildmylife.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import br.com.jesusc.rebuildmylife.enums.EnumNotification
import br.com.jesusc.rebuildmylife.enums.EnumPriority
import br.com.jesusc.rebuildmylife.model.Schedule
import br.com.jesusc.rebuildmylife.model.Task
import br.com.jesusc.rebuildmylife.model.UiDate
import com.google.gson.Gson
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

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
                enumPriority INTEGER NOT NULL, 
                date INTEGER NOT NULL, 
                repeat TEXT NOT NULL, 
                notification INTEGER NOT NULL,
                checked INTEGER DEFAULT 0
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
            put("enumPriority", task.enumPriority.value) // Salvamos como String
            put("date", task.date)
            put("repeat", task.repeat.toJson()) // Convertido para JSON
            put("notification", task.notification.value) // Convertido para JSON
            put("checked", if(!task.checked) 0 else 1)
        }
        return db.insert(TABLE_TASK, null, values)
    }

    // Atualizar uma tarefa
    fun updateTask(task: Task): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", task.title)
            put("description", task.description)
            put("enumPriority", task.enumPriority.value)
            put("date", task.date)
            put("repeat", task.repeat.toJson())
            put("notification", task.notification.value)
            put("checked", if(!task.checked) 0 else 1)
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

    fun getTasksByDate(selectedDate: UiDate): MutableList<Task> {
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_TASK WHERE date = ?" +
                    " OR repeat LIKE ?\n",
            arrayOf( selectedDate.date.toString(), "%${selectedDate.dayOfWeek}%")
        )

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
    val enumPriority = EnumPriority.fromValue(getInt(getColumnIndexOrThrow("enumPriority")))
    val date = getLong(getColumnIndexOrThrow("date"))
    val repeat = Schedule.fromJson(getString(getColumnIndexOrThrow("repeat")))
    val notification = EnumNotification.fromValue(getInt(getColumnIndexOrThrow("notification")))
    val checked = getInt(getColumnIndexOrThrow("checked")) == 1

    return Task(id, title, description, enumPriority, date, repeat, notification, checked)
}

// Métodos de serialização e desserialização para Schedule
fun Schedule.Companion.fromJson(json: String): Schedule {
    return Gson().fromJson(json, Schedule::class.java)
}

fun String.toMonthNumber(): String {
    return when (lowercase()) {
        "janeiro" -> "01"
        "fevereiro" -> "02"
        "março" -> "03"
        "abril" -> "04"
        "maio" -> "05"
        "junho" -> "06"
        "julho" -> "07"
        "agosto" -> "08"
        "setembro" -> "09"
        "outubro" -> "10"
        "novembro" -> "11"
        "dezembro" -> "12"
        else -> error("Mês inválido: $this")
    }
}


// Métodos de serialização e desserialização para Notification
//fun Notification.Companion.fromJson(json: String): Notification {
//    return Gson().fromJson(json, Notification::class.java)
//}
