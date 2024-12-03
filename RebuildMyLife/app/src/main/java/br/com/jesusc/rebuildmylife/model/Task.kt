package br.com.jesusc.rebuildmylife.model

import br.com.jesusc.rebuildmylife.enums.Priority

data class Task(val title:String, val description:String, val priority:Priority, val repeat: Shedule,
                val notification:Notification)
