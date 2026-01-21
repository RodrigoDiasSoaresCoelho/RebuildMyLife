package br.com.jesusc.rebuildmylife.enums

enum class EnumNotification(val value: Int) {
    SIMPLE(0),
    SCHEDULE(1),
    FULL_SCREAM(2);

    companion object {
        fun fromValue(value: Int): EnumNotification {
            return EnumNotification.values().firstOrNull { it.value == value }
                ?: SIMPLE
        }
    }
}