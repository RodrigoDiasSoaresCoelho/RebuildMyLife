package br.com.jesusc.rebuildmylife.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission

class TaskAlarmReceiver : BroadcastReceiver() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("TITLE") ?: "Tarefa"
        val message = intent.getStringExtra("MESSAGE") ?: ""
        val type = intent.getStringExtra("TYPE") ?: NotificationType.SIMPLE.name

        NotificationHelper.showNotification(
            context,
            title,
            message,
            NotificationType.valueOf(type)
        )
    }
}