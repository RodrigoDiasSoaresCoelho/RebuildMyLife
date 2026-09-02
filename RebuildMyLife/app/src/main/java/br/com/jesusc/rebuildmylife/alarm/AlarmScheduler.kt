package br.com.jesusc.rebuildmylife.alarm

interface AlarmScheduler {

    fun schedule(alarm: AlarmData): Boolean

    fun cancel(alarmId: Long)

    fun snooze(
        alarm: AlarmData,
        minutes: Int = alarm.snoozeMinutes
    ): Boolean
}