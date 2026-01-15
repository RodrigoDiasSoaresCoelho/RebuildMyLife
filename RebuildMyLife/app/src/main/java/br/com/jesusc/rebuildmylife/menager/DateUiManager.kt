package br.com.jesusc.rebuildmylife.menager

import androidx.recyclerview.widget.RecyclerView
import br.com.jesusc.rebuildmylife.model.UiDate
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class DateUiManager {

    private var currentYearMonth: YearMonth = YearMonth.now()
    private var selectedDate: LocalDate? = LocalDate.now()

    private val locale = Locale("pt", "BR")

    fun getCurrentMonthLabel(): String {
        val month = currentYearMonth.month
            .getDisplayName(TextStyle.FULL, locale)
            .replaceFirstChar { it.uppercase() }

        return "$month ${currentYearMonth.year}"
    }

    fun getDatesForUi(): MutableList<UiDate> {
        return generateDates(currentYearMonth)
    }

    fun goToNextMonth(): List<UiDate> {
        currentYearMonth = currentYearMonth.plusMonths(1)
        adjustSelectedDate()
        return getDatesForUi()
    }

    fun goToPreviousMonth(): List<UiDate> {
        currentYearMonth = currentYearMonth.minusMonths(1)
        adjustSelectedDate()
        return getDatesForUi()
    }

    fun selectDate(day: Int) {
        selectedDate = LocalDate.of(
            currentYearMonth.year,
            currentYearMonth.monthValue,
            day
        )
    }

    fun getSelectedDate(): UiDate? {
        val date = selectedDate ?: return null
        return mapToUiDate(date)
    }

    // -----------------------
    // Internals
    // -----------------------

    private fun generateDates(yearMonth: YearMonth): MutableList<UiDate> {
        val today = LocalDate.now()

        return (1..yearMonth.lengthOfMonth()).map { day ->
            val date = LocalDate.of(yearMonth.year, yearMonth.monthValue, day)

            UiDate(
                day = day.toString(),
                dayOfWeek = date.dayOfWeek
                    .getDisplayName(TextStyle.SHORT, locale)
                    .uppercase(),
                month = date.month
                    .getDisplayName(TextStyle.FULL, locale),
                year = yearMonth.year.toString()
            )
        } as MutableList<UiDate>
    }

    private fun adjustSelectedDate() {
        selectedDate?.let {
            if (it.year != currentYearMonth.year ||
                it.month != currentYearMonth.month
            ) {
                selectedDate = null
            }
        }
    }

    private fun mapToUiDate(date: LocalDate): UiDate {
        return UiDate(
            day = date.dayOfMonth.toString(),
            dayOfWeek = date.dayOfWeek
                .getDisplayName(TextStyle.SHORT, locale)
                .uppercase(),
            month = date.month
                .getDisplayName(TextStyle.FULL, locale),
            year = date.year.toString()
        )
    }

}