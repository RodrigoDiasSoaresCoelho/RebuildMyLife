package br.com.jesusc.rebuildmylife.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        if (intent.action != ACTION_ALARM) {
            return
        }

        val alarmId =
            intent.getLongExtra(EXTRA_ALARM_ID, -1L)

        if (alarmId == -1L) {
            return
        }

        val typeString =
            intent.getStringExtra(EXTRA_ALARM_TYPE)
                ?: AlarmType.NOTIFICATION.name

        val type = try {
            AlarmType.valueOf(typeString)
        } catch (_: Exception) {
            AlarmType.NOTIFICATION
        }

        val title =
            intent.getStringExtra(EXTRA_TITLE)
                ?: "Lembrete"

        val message =
            intent.getStringExtra(EXTRA_MESSAGE)
                ?: "Você tem uma tarefa pendente."

        val snoozeMinutes =
            intent.getIntExtra(
                EXTRA_SNOOZE_MINUTES,
                5
            )

        val alarm = AlarmData(
            id = alarmId,
            triggerAtMillis = System.currentTimeMillis(),
            type = type,
            title = title,
            message = message,
            snoozeMinutes = snoozeMinutes
        )

        val notificationManager =
            AlarmNotificationManager(context)

        notificationManager.show(alarm)
    }

    companion object {

        const val ACTION_ALARM =
            "com.seuapp.alarm.ACTION_ALARM"

        const val EXTRA_ALARM_ID =
            "extra_alarm_id"

        const val EXTRA_ALARM_TYPE =
            "extra_alarm_type"

        const val EXTRA_TITLE =
            "extra_title"

        const val EXTRA_MESSAGE =
            "extra_message"

        const val EXTRA_SNOOZE_MINUTES =
            "extra_snooze_minutes"
    }
}