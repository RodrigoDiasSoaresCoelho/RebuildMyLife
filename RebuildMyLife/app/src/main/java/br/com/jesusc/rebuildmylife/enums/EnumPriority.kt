package br.com.jesusc.rebuildmylife.enums

enum class EnumPriority(val value: Int) {
    URGENT(0),
    HIGH(1),
    MEDIUM(2),
    LOW(3);

    companion object {
        fun fromValue(value: Int): EnumPriority {
            return values().firstOrNull { it.value == value }
                ?: LOW // fallback seguro
        }
    }
}