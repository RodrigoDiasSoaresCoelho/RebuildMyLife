package br.com.jesusc.rebuildmylife.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.jesusc.rebuildmylife.R
import br.com.jesusc.rebuildmylife.databinding.FragmentRepeatBinding
import br.com.jesusc.rebuildmylife.enums.EnumSchedule
import br.com.jesusc.rebuildmylife.model.Task

class RepeatFragment : Fragment() {
    private lateinit var binding: FragmentRepeatBinding
    private lateinit var task: Task
    private var edit = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepeatBinding.inflate(inflater, container, false)


        binding.imgBack.setOnClickListener {

        }

        binding.rbNever.setOnClickListener { v ->
            setUnselected()
            binding.rbNever.isChecked = true
        }

        binding.rbDaily.setOnClickListener { v ->
            setUnselected()
            binding.rbDaily.isChecked = true
            setDaily(VISIBLE)
        }

        binding.rbWeekly.setOnClickListener { v ->
            setUnselected()
            binding.rbWeekly.isChecked = true
            setWeekly(VISIBLE)
        }

        binding.rbMonthly.setOnClickListener { v ->
            setUnselected()
            binding.rbMonthly.isChecked = true
            setMonthly(VISIBLE)
        }

        binding.rbYearly.setOnClickListener { v ->
            setUnselected()
            binding.rbYearly.isChecked = true
            setYearly(VISIBLE)
        }


        binding.sundayLayout.setOnClickListener { setSchedule(binding.sundayLayout) }
        binding.LayoutMonday.setOnClickListener { setSchedule(binding.LayoutMonday) }
        binding.LayoutTue.setOnClickListener { setSchedule(binding.LayoutTue) }
        binding.LayoutWednesday.setOnClickListener { setSchedule(binding.LayoutWednesday) }
        binding.LayoutThursday.setOnClickListener { setSchedule(binding.LayoutThursday) }
        binding.LayoutFriday.setOnClickListener { setSchedule(binding.LayoutFriday) }
        binding.LayoutSaturday.setOnClickListener { setSchedule(binding.LayoutSaturday) }


        binding.btnRepeatOnTheDay.setOnClickListener {
            setDefaultBtn()
            setBtnSelected(binding.btnRepeatOnTheDay, binding.RepeatOnTheDay)
        }

        binding.btnRepeatOnTheDayOfTheWeek.setOnClickListener {
            setDefaultBtn()
            setBtnSelected(binding.btnRepeatOnTheDayOfTheWeek, binding.RepeatOnTheDayOfTheWeek)
        }

        binding.btnSelectDateToRepeat.setOnClickListener {
            setDefaultBtn()
            setBtnSelected(binding.btnSelectDateToRepeat, binding.txtSelectDateToRepeat)
            binding.ContainerDaysOfMonth.visibility = VISIBLE
        }

        binding.btnRepeatOnMonthDay.setOnClickListener {
            setDefaultBtn()
            setBtnSelected(binding.btnRepeatOnMonthDay, binding.txtRepeatOnMonthDay)
        }

        binding.btnRepeatOnTheDayOfTheWeekOfTheMonth.setOnClickListener {
            setDefaultBtn()
            setBtnSelected(binding.btnRepeatOnTheDayOfTheWeekOfTheMonth, binding.txtRepeatOnTheDayOfTheWeekOfTheMonth)
        }

        binding.btnSelectTheMonth.setOnClickListener {
            setDefaultBtn()
            setBtnSelected(binding.btnSelectTheMonth, binding.txtSelectTheMonth)
            binding.ContainerMonths.visibility = VISIBLE
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RepeatFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    init {
        try {
            task = requireArguments().getSerializable("task") as Task

            if (!task.repeat.getList().isEmpty()) {
                task.repeat.getList().forEach { shedule ->
//                    editSchedule()
                }

            }

            edit = true
        } catch (e: Exception) {
            e.printStackTrace()
//            var selectedDateFronTasks = requireArguments().getLong("selectedDate")
//            this.selectedDate = LocalDate.ofEpochDay(selectedDateFronTasks)

            task = Task()


//            val dataFormatada = converter.getLocalData(selectedDateFronTasks)
//
//
//            binding.txtDate.setText(dataFormatada)
            edit = false
        }
    }

    private fun setUnselected() {
        binding.rbNever.isChecked = false
        binding.rbDaily.isChecked = false
        binding.rbWeekly.isChecked = false
        binding.rbMonthly.isChecked = false
        binding.rbYearly.isChecked = false

        setDaily(GONE)
        setWeekly(GONE)
        setMonthly(GONE)
        setYearly(GONE)

        binding.ContainerDaysOfMonth.visibility = GONE
        binding.ContainerMonths.visibility = GONE
    }

    private fun setDaily(visible: Int) {
        binding.edtDaily.visibility = visible
        binding.txtDaily2.visibility = visible
        binding.txtDuration.visibility = visible
        binding.durationContainer.visibility = visible
    }

    private fun setWeekly(visible: Int) {
        binding.edtWeekly.visibility = visible
        binding.txtWeekly2.visibility = visible
        binding.repeatContainer.visibility = visible
        binding.txtDuration.visibility = visible
        binding.durationContainer.visibility = visible
    }

    private fun setMonthly(visible: Int) {
        binding.edtMonthly.visibility = visible
        binding.txtMonthly2.visibility = visible
        binding.ContainerOptionsOfMonth.visibility = visible
        binding.txtDuration.visibility = visible
        binding.durationContainer.visibility = visible
    }

    private fun setYearly(visible: Int) {
        binding.edtYearly.visibility = visible
        binding.txtYearly2.visibility = visible
        binding.ContainerOptionsOfYear.visibility = visible
        binding.txtDuration.visibility = visible
        binding.durationContainer.visibility = visible
    }

    private fun setSchedule(view: ConstraintLayout) {
        when (view.id) {
            binding.sundayLayout.id -> {
                if (!task.repeat.getList().contains(EnumSchedule.Sunday.value)) {
                    task.repeat.addSchedule(EnumSchedule.Sunday)
                    binding.sundayLayout.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvSun.setTextColor(Color.WHITE)
                } else {
                    task.repeat.removeSchedule(EnumSchedule.Sunday)
                    binding.sundayLayout.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvSun.setTextColor(Color.GRAY)
                }
            }

            binding.LayoutMonday.id -> {
                if (!task.repeat.getList().contains(EnumSchedule.Monday.value)) {
                    task.repeat.addSchedule(EnumSchedule.Monday)
                    binding.LayoutMonday.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvMon.setTextColor(Color.WHITE)
                } else {
                    task.repeat.removeSchedule(EnumSchedule.Monday)
                    binding.LayoutMonday.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvMon.setTextColor(Color.GRAY)
                }
            }

            binding.LayoutTue.id -> {
                if (!task.repeat.getList().contains(EnumSchedule.Tuesday.value)) {
                    task.repeat.addSchedule(EnumSchedule.Tuesday)
                    binding.LayoutTue.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvTue.setTextColor(Color.WHITE)
                } else {
                    task.repeat.removeSchedule(EnumSchedule.Tuesday)
                    binding.LayoutTue.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvTue.setTextColor(Color.GRAY)
                }
            }

            binding.LayoutWednesday.id -> {
                if (!task.repeat.getList().contains(EnumSchedule.Wednesday.value)) {
                    task.repeat.addSchedule(EnumSchedule.Wednesday)
                    binding.LayoutWednesday.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvWed.setTextColor(Color.WHITE)
                } else {
                    task.repeat.removeSchedule(EnumSchedule.Wednesday)
                    binding.LayoutWednesday.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvWed.setTextColor(Color.GRAY)
                }
            }

            binding.LayoutThursday.id -> {
                if (!task.repeat.getList().contains(EnumSchedule.Thursday.value)) {
                    task.repeat.addSchedule(EnumSchedule.Thursday)
                    binding.LayoutThursday.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvThu.setTextColor(Color.WHITE)
                } else {
                    task.repeat.removeSchedule(EnumSchedule.Thursday)
                    binding.LayoutThursday.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvThu.setTextColor(Color.GRAY)
                }
            }

            binding.LayoutFriday.id -> {
                if (!task.repeat.getList().contains(EnumSchedule.Friday.value)) {
                    task.repeat.addSchedule(EnumSchedule.Friday)
                    binding.LayoutFriday.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvFri.setTextColor(Color.WHITE)
                } else {
                    task.repeat.removeSchedule(EnumSchedule.Friday)
                    binding.LayoutFriday.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvFri.setTextColor(Color.GRAY)
                }
            }

            binding.LayoutSaturday.id -> {
                if (!task.repeat.getList().contains(EnumSchedule.Saturday.value)) {
                    task.repeat.addSchedule(EnumSchedule.Saturday)
                    binding.LayoutSaturday.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvSat.setTextColor(Color.WHITE)
                } else {
                    task.repeat.removeSchedule(EnumSchedule.Saturday)
                    binding.LayoutSaturday.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvSat.setTextColor(Color.GRAY)
                }
            }
        }
    }

    private fun editSchedule(view: ConstraintLayout) {
        when (view.id) {
            binding.sundayLayout.id -> {
                binding.sundayLayout.setBackgroundResource(R.drawable.schedule_selected)
                binding.tvSun.setTextColor(Color.WHITE)
            }

            binding.LayoutMonday.id -> {
                binding.LayoutMonday.setBackgroundResource(R.drawable.schedule_selected)
                binding.tvMon.setTextColor(Color.WHITE)
            }

            binding.LayoutTue.id -> {
                binding.LayoutTue.setBackgroundResource(R.drawable.schedule_selected)
                binding.tvTue.setTextColor(Color.WHITE)
            }

            binding.LayoutWednesday.id -> {
                binding.LayoutWednesday.setBackgroundResource(R.drawable.schedule_selected)
                binding.tvWed.setTextColor(Color.WHITE)
            }

            binding.LayoutThursday.id -> {
                binding.LayoutThursday.setBackgroundResource(R.drawable.schedule_selected)
                binding.tvThu.setTextColor(Color.WHITE)
            }

            binding.LayoutFriday.id -> {
                binding.LayoutFriday.setBackgroundResource(R.drawable.schedule_selected)
                binding.tvFri.setTextColor(Color.WHITE)
            }

            binding.LayoutSaturday.id -> {
                binding.LayoutSaturday.setBackgroundResource(R.drawable.schedule_selected)
                binding.tvSat.setTextColor(Color.WHITE)
            }
        }
    }

    private fun setDefaultBtn() {
        setBtnDefault(binding.btnRepeatOnTheDay, binding.RepeatOnTheDay)
        setBtnDefault(binding.btnRepeatOnTheDayOfTheWeek, binding.RepeatOnTheDayOfTheWeek)
        setBtnDefault(binding.btnSelectDateToRepeat, binding.txtSelectDateToRepeat)
        setBtnDefault(binding.btnRepeatOnMonthDay, binding.txtRepeatOnMonthDay)
        setBtnDefault(binding.btnRepeatOnTheDayOfTheWeekOfTheMonth, binding.txtRepeatOnTheDayOfTheWeekOfTheMonth)
        setBtnDefault(binding.btnSelectTheMonth, binding.txtSelectTheMonth)


        binding.ContainerDaysOfMonth.visibility = GONE
        binding.ContainerMonths.visibility = GONE
    }

    private fun setBtnDefault(btn: ConstraintLayout, txt: TextView) {
        btn.setBackgroundResource(R.drawable.buttons_default)
        txt.setTextColor(Color.BLACK)
    }

    private fun setBtnSelected(btn: ConstraintLayout, txt: TextView) {
        btn.setBackgroundResource(R.drawable.button_selected)
        txt.setTextColor(Color.WHITE)
    }
}