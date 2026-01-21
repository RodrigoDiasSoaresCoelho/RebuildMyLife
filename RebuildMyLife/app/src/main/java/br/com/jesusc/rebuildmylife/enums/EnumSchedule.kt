package br.com.jesusc.rebuildmylife.enums


enum class EnumSchedule(val value: Int) {
    Monday(1),
    Tuesday(2),
    Wednesday(3),
    Thursday(4),
    Friday(5),
    Saturday(6),
    Sunday(7);

    companion object {
        fun fromValue(value: Int): EnumSchedule {
            return values().firstOrNull { it.value == value } ?: Monday
        }
    }
}