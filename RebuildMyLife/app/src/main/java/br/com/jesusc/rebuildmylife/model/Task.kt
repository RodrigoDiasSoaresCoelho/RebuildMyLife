package br.com.jesusc.rebuildmylife.model

import br.com.jesusc.rebuildmylife.enums.EnumPriority
import br.com.jesusc.rebuildmylife.enums.EnumSchedule

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // ID auto-incrementado
    val title: String="",
    val description: String="",
    var enumPriority: EnumPriority=EnumPriority.MEDIUM,
    val repeat: Schedule=Schedule(), // Alterado para lista de Schedule
    val notification: Notification = Notification()
)