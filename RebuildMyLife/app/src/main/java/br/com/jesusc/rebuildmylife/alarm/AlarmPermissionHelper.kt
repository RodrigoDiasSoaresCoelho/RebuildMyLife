package br.com.jesusc.rebuildmylife.alarm

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

object AlarmPermissionHelper {

    fun canScheduleExactAlarms(
        context: Context
    ): Boolean {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return true
        }

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        return alarmManager.canScheduleExactAlarms()
    }

    fun openExactAlarmSettings(
        context: Context
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            val intent = Intent(
                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                Uri.parse(
                    "package:${context.packageName}"
                )
            )

            context.startActivity(intent)
        }
    }

    fun canUseFullScreenIntent(
        context: Context
    ): Boolean {

        if (Build.VERSION.SDK_INT < 34) {
            return true
        }

        val manager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        return manager.canUseFullScreenIntent()
    }

    fun openFullScreenSettings(
        context: Context
    ) {

        if (Build.VERSION.SDK_INT >= 34) {

            val intent = Intent(
                Settings.ACTION_MANAGE_APP_USE_FULL_SCREEN_INTENT
            ).apply {

                data = Uri.parse(
                    "package:${context.packageName}"
                )
            }

            context.startActivity(intent)
        }
    }

    fun hasNotificationPermission(
        context: Context
    ): Boolean {

        if (
            Build.VERSION.SDK_INT <
            Build.VERSION_CODES.TIRAMISU
        ) {
            return true
        }

        return context.checkSelfPermission(
            Manifest.permission.POST_NOTIFICATIONS
        ) ==
                android.content.pm.PackageManager.PERMISSION_GRANTED
    }
}