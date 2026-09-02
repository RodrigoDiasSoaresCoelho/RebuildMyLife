package br.com.jesusc.rebuildmylife.alarm

data class AlarmData(
    val id: Long,
    val triggerAtMillis: Long,
    val type: AlarmType,
    val title: String,
    val message: String,
    val snoozeMinutes: Int = 5
)