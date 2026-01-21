package br.com.jesusc.rebuildmylife.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import br.com.jesusc.rebuildmylife.databinding.DialogMonthYearPickerBinding
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class MonthYearPickerDialog(
    context: Context,
    private val initialYearMonth: YearMonth = YearMonth.now(),
    private val onDateSelected: (YearMonth) -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogMonthYearPickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogMonthYearPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMonthPicker()
        setupYearPicker()

        binding.monthPicker.value = initialYearMonth.monthValue - 1
        binding.yearPicker.value = initialYearMonth.year

        binding.btnOkNumberPicker.setOnClickListener {
            val selectedMonth = binding.monthPicker.value + 1
            val selectedYear = binding.yearPicker.value

            onDateSelected(YearMonth.of(selectedYear, selectedMonth))
            dismiss()
        }

        binding.btnCancelNumberPicker.setOnClickListener {
            dismiss()
        }
    }

    private fun setupMonthPicker() {
        val months = Month.values().map {
            it.getDisplayName(TextStyle.FULL, Locale.getDefault())
        }

        binding.monthPicker.minValue = 0
        binding.monthPicker.maxValue = months.size - 1
        binding.monthPicker.displayedValues = months.toTypedArray()
        binding.monthPicker.wrapSelectorWheel = true
    }

    private fun setupYearPicker() {
        binding.yearPicker.minValue = 2000
        binding.yearPicker.maxValue = 2100
        binding.yearPicker.wrapSelectorWheel = false
    }
}
