package br.com.jesusc.rebuildmylife.model

import br.com.jesusc.rebuildmylife.enums.EnumNotification
import br.com.jesusc.rebuildmylife.enums.EnumPriority
import java.io.Serializable


data class Task(
    val id: Long = 0,
    var title: String = "",
    var description: String = "",
    var enumPriority: EnumPriority = EnumPriority.MEDIUM,
    var date: Long = 0,
    var repeat: Schedule = Schedule(),
    var notification: EnumNotification = EnumNotification.SIMPLE,
    var checked: Boolean = false
): Serializable
