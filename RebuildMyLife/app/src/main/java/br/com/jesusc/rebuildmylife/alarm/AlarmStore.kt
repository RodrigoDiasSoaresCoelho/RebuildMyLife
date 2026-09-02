package br.com.jesusc.rebuildmylife.alarm

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class AlarmStore(
    context: Context
) {

    private val preferences =
        context.getSharedPreferences(
            "alarm_store",
            Context.MODE_PRIVATE
        )

    private val scheduler =
        AlarmSchedulerImpl(context)

    fun save(alarm: AlarmData) {

        val alarms = getAll()
            .filter { it.id != alarm.id }
            .toMutableList()

        alarms.add(alarm)

        persist(alarms)
    }

    fun remove(id: Long) {

        val alarms =
            getAll()
                .filter { it.id != id }

        persist(alarms)
    }

    fun getAll(): List<AlarmData> {

        val raw =
            preferences.getString(
                KEY_ALARMS,
                null
            ) ?: return emptyList()

        return try {

            val array =
                JSONArray(raw)

            buildList {

                for (i in 0 until array.length()) {

                    val json =
                        array.getJSONObject(i)

                    add(
                        AlarmData(
                            id =
                                json.getLong("id"),

                            triggerAtMillis =
                                json.getLong(
                                    "triggerAtMillis"
                                ),

                            type =
                                AlarmType.valueOf(
                                    json.getString("type")
                                ),

                            title =
                                json.getString("title"),

                            message =
                                json.getString("message"),

                            snoozeMinutes =
                                json.optInt(
                                    "snoozeMinutes",
                                    5
                                )
                        )
                    )
                }
            }

        } catch (_: Exception) {

            emptyList()
        }
    }

    fun rescheduleAll() {

        val now =
            System.currentTimeMillis()

        getAll()
            .filter {
                it.triggerAtMillis > now
            }
            .forEach {
                scheduler.schedule(it)
            }
    }

    private fun persist(
        alarms: List<AlarmData>
    ) {

        val array = JSONArray()

        alarms.forEach { alarm ->

            val json =
                JSONObject().apply {

                    put(
                        "id",
                        alarm.id
                    )

                    put(
                        "triggerAtMillis",
                        alarm.triggerAtMillis
                    )

                    put(
                        "type",
                        alarm.type.name
                    )

                    put(
                        "title",
                        alarm.title
                    )

                    put(
                        "message",
                        alarm.message
                    )

                    put(
                        "snoozeMinutes",
                        alarm.snoozeMinutes
                    )
                }

            array.put(json)
        }

        preferences
            .edit()
            .putString(
                KEY_ALARMS,
                array.toString()
            )
            .apply()
    }

    companion object {

        private const val KEY_ALARMS =
            "alarms"
    }
}