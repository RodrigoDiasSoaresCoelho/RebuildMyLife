package br.com.jesusc.rebuildmylife.model

import br.com.jesusc.rebuildmylife.enums.EnumPriority

data class Task(val title:String, val description:String, val enumPriority:EnumPriority, val repeat: Schedule,
                val notification:Notification)
