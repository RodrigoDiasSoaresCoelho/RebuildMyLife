package br.com.jesusc.rebuildmylife.menager

import br.com.jesusc.rebuildmylife.model.UiDate
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class DateUiManager {

    private var currentYearMonth: YearMonth = YearMonth.now()
    private var selectedDate: LocalDate? = LocalDate.now()

    private val locale: Locale
        get() = Locale.getDefault()

    // -----------------------
    // Public API
    // -----------------------

    fun setYearMonth(yearMonth: YearMonth) {
        currentYearMonth = yearMonth
        adjustSelectedDate()
    }

    fun getCurrentYearMonth(): YearMonth {
        return currentYearMonth
    }

    fun getCurrentMonthLabel(): String {
        val monthName = currentYearMonth.month
            .getDisplayName(TextStyle.FULL, locale)
            .replaceFirstChar { it.uppercase(locale) }

        return "$monthName ${currentYearMonth.year}"
    }

    fun getDatesForUi(): MutableList<UiDate> {
        return generateDates(currentYearMonth)
    }

    fun goToNextMonth(): MutableList<UiDate> {
        currentYearMonth = currentYearMonth.plusMonths(1)
        adjustSelectedDate()
        return getDatesForUi()
    }

    fun goToPreviousMonth(): MutableList<UiDate> {
        currentYearMonth = currentYearMonth.minusMonths(1)
        adjustSelectedDate()
        return getDatesForUi()
    }

    fun selectDate(epochDay: Long) {
        selectedDate = LocalDate.ofEpochDay(epochDay)
    }

    fun getSelectedDate(): UiDate? {
        return selectedDate?.let { mapToUiDate(it) }
    }

    fun selectFirstValidDate(): UiDate {
        val firstDate = currentYearMonth.atDay(1)
        selectedDate = firstDate
        return mapToUiDate(firstDate)
    }

    fun goToToday() {
        val today = LocalDate.now()
        currentYearMonth = YearMonth.from(today)
        selectedDate = today
    }


    // -----------------------
    // Internals
    // -----------------------

    private fun generateDates(yearMonth: YearMonth): MutableList<UiDate> {
        return (1..yearMonth.lengthOfMonth()).map { day ->
            val date = yearMonth.atDay(day)

            UiDate(
                date = date.toEpochDay(),
                dayOfWeek = date.dayOfWeek.value // 1 (Mon) .. 7 (Sun)
            )
        }.toMutableList()
    }

    private fun adjustSelectedDate() {
        selectedDate?.let {
            if (YearMonth.from(it) != currentYearMonth) {
                selectedDate = null
            }
        }
    }

    private fun mapToUiDate(date: LocalDate): UiDate {
        return UiDate(
            date = date.toEpochDay(),
            dayOfWeek = date.dayOfWeek.value
        )
    }
}
