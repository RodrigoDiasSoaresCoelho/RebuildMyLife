package br.com.jesusc.rebuildmylife.alarm

import android.app.KeyguardManager
import android.content.Context
import android.os.Bundle
import android.view.WindowManager

import androidx.appcompat.app.AppCompatActivity
import br.com.jesusc.rebuildmylife.databinding.ActivityAlarmBinding


class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding

    private lateinit var soundManager: AlarmSoundManager

    private var alarmId: Long = -1L

    private var snoozeMinutes: Int = 5

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        showOverLockScreen()

        binding =
            ActivityAlarmBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        alarmId =
            intent.getLongExtra(
                AlarmReceiver.EXTRA_ALARM_ID,
                -1L
            )

        val title =
            intent.getStringExtra(
                AlarmReceiver.EXTRA_TITLE
            ) ?: "Lembrete"

        val message =
            intent.getStringExtra(
                AlarmReceiver.EXTRA_MESSAGE
            ) ?: ""

        snoozeMinutes =
            intent.getIntExtra(
                AlarmReceiver.EXTRA_SNOOZE_MINUTES,
                5
            )

        binding.textTitle.text = title
        binding.textMessage.text = message

        soundManager =
            AlarmSoundManager(this)

        soundManager.start()

        binding.buttonDismiss.setOnClickListener {
            dismissAlarm()
        }

        binding.buttonSnooze.setOnClickListener {
            snoozeAlarm()
        }
    }

    private fun dismissAlarm() {

        soundManager.stop()

        AlarmNotificationManager(this)
            .cancel(alarmId)

        finish()
    }

    private fun snoozeAlarm() {

        soundManager.stop()

        AlarmNotificationManager(this)
            .cancel(alarmId)

        val alarmScheduler =
            AlarmSchedulerImpl(this)

        val alarm =
            AlarmData(
                id = alarmId,
                triggerAtMillis =
                    System.currentTimeMillis(),
                type = AlarmType.FULLSCREEN,
                title =
                    binding.textTitle.text.toString(),
                message =
                    binding.textMessage.text.toString(),
                snoozeMinutes = snoozeMinutes
            )

        alarmScheduler.snooze(
            alarm,
            snoozeMinutes
        )

        finish()
    }

    private fun showOverLockScreen() {

        if (android.os.Build.VERSION.SDK_INT >= 27) {

            setShowWhenLocked(true)

            setTurnScreenOn(true)
        } else {

            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
            )

            window.addFlags(
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }

        val keyguardManager =
            getSystemService(
                Context.KEYGUARD_SERVICE
            ) as KeyguardManager

        if (
            android.os.Build.VERSION.SDK_INT >= 27 &&
            keyguardManager.isKeyguardLocked
        ) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }
    }

    override fun onDestroy() {

        soundManager.stop()

        super.onDestroy()
    }
}