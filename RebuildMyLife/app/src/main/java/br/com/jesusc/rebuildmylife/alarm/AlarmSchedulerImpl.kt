package br.com.jesusc.rebuildmylife.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings

class AlarmSchedulerImpl(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager: AlarmManager by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override fun schedule(alarm: AlarmData): Boolean {

        if (alarm.triggerAtMillis <= System.currentTimeMillis()) {
            return false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            if (!alarmManager.canScheduleExactAlarms()) {
                return false
            }
        }

        val pendingIntent =
            createPendingIntent(alarm)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarm.triggerAtMillis,
            pendingIntent
        )

        AlarmStore(context).save(alarm)

        return true
    }

    override fun cancel(alarmId: Long) {

        val intent = Intent(
            context,
            AlarmReceiver::class.java
        ).apply {
            action = AlarmReceiver.ACTION_ALARM

            putExtra(
                AlarmReceiver.EXTRA_ALARM_ID,
                alarmId
            )
        }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                alarmId.toRequestCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()

        AlarmStore(context).remove(alarmId)
    }

    override fun snooze(
        alarm: AlarmData,
        minutes: Int
    ): Boolean {

        cancel(alarm.id)

        val snoozedAlarm = alarm.copy(
            triggerAtMillis =
                System.currentTimeMillis() +
                        minutes * 60_000L
        )

        return schedule(snoozedAlarm)
    }

    private fun createPendingIntent(
        alarm: AlarmData
    ): PendingIntent {

        val intent = Intent(
            context,
            AlarmReceiver::class.java
        ).apply {

            action = AlarmReceiver.ACTION_ALARM

            putExtra(
                AlarmReceiver.EXTRA_ALARM_ID,
                alarm.id
            )

            putExtra(
                AlarmReceiver.EXTRA_ALARM_TYPE,
                alarm.type.name
            )

            putExtra(
                AlarmReceiver.EXTRA_TITLE,
                alarm.title
            )

            putExtra(
                AlarmReceiver.EXTRA_MESSAGE,
                alarm.message
            )

            putExtra(
                AlarmReceiver.EXTRA_SNOOZE_MINUTES,
                alarm.snoozeMinutes
            )
        }

        return PendingIntent.getBroadcast(
            context,
            alarm.id.toRequestCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {

        private fun Long.toRequestCode(): Int {
            return (this xor (this ushr 32)).toInt()
        }
    }
}