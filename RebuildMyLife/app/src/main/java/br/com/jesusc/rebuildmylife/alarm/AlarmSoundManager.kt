package br.com.jesusc.rebuildmylife.alarm

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager

class AlarmSoundManager(
    private val context: Context
) {

    private var mediaPlayer: MediaPlayer? = null

    fun start() {

        stop()

        val uri =
            RingtoneManager.getDefaultUri(
                RingtoneManager.TYPE_ALARM
            )

        mediaPlayer =
            MediaPlayer().apply {

                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(
                            AudioAttributes.USAGE_ALARM
                        )
                        .setContentType(
                            AudioAttributes.CONTENT_TYPE_MUSIC
                        )
                        .build()
                )

                setDataSource(
                    context,
                    uri
                )

                isLooping = true

                prepare()

                start()
            }
    }

    fun stop() {

        mediaPlayer?.let {

            try {
                if (it.isPlaying) {
                    it.stop()
                }
            } catch (_: Exception) {
            }

            it.release()
        }

        mediaPlayer = null
    }
}