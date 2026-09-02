package br.com.jesusc.rebuildmylife.alarm

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.com.jesusc.rebuildmylife.R

class AlarmNotificationManager(
    private val context: Context
) {

    private val notificationManager =
        NotificationManagerCompat.from(context)

    init {
        createChannels()
    }

    fun show(alarm: AlarmData) {

        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            context.checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification =
            buildNotification(alarm)

        notificationManager.notify(
            notificationId(alarm.id),
            notification
        )
    }

    fun cancel(alarmId: Long) {

        notificationManager.cancel(
            notificationId(alarmId)
        )
    }

    private fun buildNotification(
        alarm: AlarmData
    ): Notification {

        val activityIntent = Intent(
            context,
            AlarmActivity::class.java
        ).apply {

            putExtra(
                AlarmReceiver.EXTRA_ALARM_ID,
                alarm.id
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

        val activityPendingIntent =
            PendingIntent.getActivity(
                context,
                alarm.id.toRequestCode(),
                activityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val builder =
            NotificationCompat.Builder(
                context,
                channelFor(alarm.type)
            )
                .setSmallIcon(
                    R.drawable.ic_alarm
                )
                .setContentTitle(alarm.title)
                .setContentText(alarm.message)
                .setContentIntent(activityPendingIntent)
                .setAutoCancel(false)
                .setOngoing(
                    alarm.type != AlarmType.NOTIFICATION
                )
                .setCategory(
                    when (alarm.type) {
                        AlarmType.NOTIFICATION ->
                            NotificationCompat.CATEGORY_REMINDER

                        AlarmType.ALARM,
                        AlarmType.FULLSCREEN ->
                            NotificationCompat.CATEGORY_ALARM
                    }
                )

        if (alarm.type != AlarmType.NOTIFICATION) {

            builder
                .setPriority(
                    NotificationCompat.PRIORITY_MAX
                )
                .setVisibility(
                    NotificationCompat.VISIBILITY_PUBLIC
                )
                .setDefaults(
                    NotificationCompat.DEFAULT_VIBRATE
                )
        }

        if (alarm.type == AlarmType.FULLSCREEN) {

            builder.setFullScreenIntent(
                activityPendingIntent,
                true
            )
        }

        return builder.build()
    }

    private fun createChannels() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val normalChannel =
            NotificationChannel(
                CHANNEL_NORMAL,
                "Lembretes",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {

                description =
                    "Notificações de tarefas e lembretes"
            }

        val alarmChannel =
            NotificationChannel(
                CHANNEL_ALARM,
                "Alarmes",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {

                description =
                    "Alarmes de tarefas"

                val soundUri =
                    RingtoneManager.getDefaultUri(
                        RingtoneManager.TYPE_ALARM
                    )

                val audioAttributes =
                    AudioAttributes.Builder()
                        .setUsage(
                            AudioAttributes.USAGE_ALARM
                        )
                        .build()

                setSound(
                    soundUri,
                    audioAttributes
                )

                enableVibration(true)

                vibrationPattern =
                    longArrayOf(
                        0,
                        500,
                        300,
                        500,
                        300,
                        1000
                    )

                lockscreenVisibility =
                    Notification.VISIBILITY_PUBLIC
            }

        val manager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        manager.createNotificationChannel(
            normalChannel
        )

        manager.createNotificationChannel(
            alarmChannel
        )
    }

    private fun channelFor(
        type: AlarmType
    ): String {

        return when (type) {

            AlarmType.NOTIFICATION ->
                CHANNEL_NORMAL

            AlarmType.ALARM,
            AlarmType.FULLSCREEN ->
                CHANNEL_ALARM
        }
    }

    private fun notificationId(
        alarmId: Long
    ): Int {

        return (alarmId xor (alarmId ushr 32)).toInt()
    }

    private fun Long.toRequestCode(): Int {
        return (this xor (this ushr 32)).toInt()
    }

    companion object {

        const val CHANNEL_NORMAL =
            "task_reminders"

        const val CHANNEL_ALARM =
            "task_alarms"
    }
}