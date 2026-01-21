package br.com.jesusc.rebuildmylife.util

import br.com.jesusc.rebuildmylife.enums.EnumSchedule
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.Locale

class ConvertData {
    fun getLocalData(date: Long): String {
        val localDate = LocalDate.ofEpochDay(date)
        val locale = Locale.getDefault()

        val formatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.SHORT)
            .withLocale(locale)

        val formattedDate = localDate.format(formatter)
        return formattedDate
    }

    fun parseUserDate(input: String): LocalDate {
        val formatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.SHORT)
            .withLocale(Locale.getDefault())

        return LocalDate.parse(input, formatter)
    }

    fun getDay(date: Long): String {
        return LocalDate.ofEpochDay(date).dayOfMonth.toString()
    }

    fun toShortName(day: Int, locale: Locale = Locale.getDefault()): String {

        val dayOfWeek = when (day) {
            EnumSchedule.Sunday.value -> DayOfWeek.SUNDAY
            EnumSchedule.Monday.value -> DayOfWeek.MONDAY
            EnumSchedule.Tuesday.value -> DayOfWeek.TUESDAY
            EnumSchedule.Wednesday.value -> DayOfWeek.WEDNESDAY
            EnumSchedule.Thursday.value -> DayOfWeek.THURSDAY
            EnumSchedule.Friday.value -> DayOfWeek.FRIDAY
            else -> {DayOfWeek.SATURDAY}
        }

        return dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
    }
}