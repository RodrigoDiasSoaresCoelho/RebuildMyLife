package br.com.jesusc.rebuildmylife.notification

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.com.jesusc.rebuildmylife.R


enum class NotificationType {
    SIMPLE,
    ALARM,
    FULLSCREEN
}

object NotificationHelper {

    private const val SIMPLE_CHANNEL = "simple_channel"
    private const val ALARM_CHANNEL = "alarm_channel"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Canal simples
        val simpleChannel = NotificationChannel(
            SIMPLE_CHANNEL,
            "Notificações simples",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        // Canal de alarme
        val alarmChannel = NotificationChannel(
            ALARM_CHANNEL,
            "Alarmes de tarefas",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableVibration(true)
            setSound(
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM),
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
            )
        }

        manager.createNotificationChannel(simpleChannel)
        manager.createNotificationChannel(alarmChannel)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(
        context: Context,
        title: String,
        message: String,
        type: NotificationType
    ) {
        when (type) {
            NotificationType.SIMPLE -> showSimple(context, title, message)
            NotificationType.ALARM -> showAlarm(context, title, message)
            NotificationType.FULLSCREEN -> showFullScreen(context, title, message)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showSimple(context: Context, title: String, message: String) {
        val notification = NotificationCompat.Builder(context, SIMPLE_CHANNEL)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(1, notification)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showAlarm(context: Context, title: String, message: String) {
        val notification = NotificationCompat.Builder(context, ALARM_CHANNEL)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle(title)
            .setContentText(message)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(2, notification)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showFullScreen(context: Context, title: String, message: String) {
//        val intent = Intent(context, TaskAlarmActivity::class.java).apply {
//            putExtra("TASK", message)
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//        }
//
//        val pendingIntent = PendingIntent.getActivity(
//            context,
//            0,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        val notification = NotificationCompat.Builder(context, ALARM_CHANNEL)
//            .setSmallIcon(R.drawable.ic_alarm)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setCategory(NotificationCompat.CATEGORY_ALARM)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setFullScreenIntent(pendingIntent, true)
//            .build()
//
//        NotificationManagerCompat.from(context).notify(3, notification)
    }
}