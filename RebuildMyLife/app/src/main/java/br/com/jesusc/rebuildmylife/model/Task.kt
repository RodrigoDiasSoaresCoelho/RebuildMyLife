package br.com.jesusc.rebuildmylife.model

import br.com.jesusc.rebuildmylife.enums.EnumPriority


data class Task(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    var enumPriority: EnumPriority = EnumPriority.MEDIUM,
    var hour: String = "",
    val repeat: Schedule = Schedule(),
    val notification: Notification = Notification()
)
