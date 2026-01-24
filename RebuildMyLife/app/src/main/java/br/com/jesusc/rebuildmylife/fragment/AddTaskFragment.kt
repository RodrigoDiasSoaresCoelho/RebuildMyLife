package br.com.jesusc.rebuildmylife.fragment

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.jesusc.rebuildmylife.R
import br.com.jesusc.rebuildmylife.databinding.FragmentAddTaskBinding
import br.com.jesusc.rebuildmylife.enums.EnumNotification
import br.com.jesusc.rebuildmylife.enums.EnumPriority
import br.com.jesusc.rebuildmylife.enums.EnumSchedule
import br.com.jesusc.rebuildmylife.helper.DbHelper
import br.com.jesusc.rebuildmylife.model.Task
import br.com.jesusc.rebuildmylife.util.ConvertData
import br.com.jesusc.rebuildmylife.util.Navigate
import br.com.jesusc.rebuildmylife.viewModel.AddTaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Calendar
import java.util.Locale

class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var task: Task
    private var edit = false
    private val converter = ConvertData()
    private var selectedDate: LocalDate? = null



    companion object {
        private val INSTANCE: AddTaskFragment by lazy {
            AddTaskFragment()
        }

        fun getInstance(): AddTaskFragment {
            return INSTANCE
        }
    }

    private val viewModel: AddTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        setupDatePicker()

        binding.LayoutUrgent.setOnClickListener {
            setPriority(binding.LayoutUrgent)
            task.enumPriority = EnumPriority.URGENT
        }

        binding.LayoutPriorityHigh.setOnClickListener {
            setPriority(binding.LayoutPriorityHigh)
            task.enumPriority = EnumPriority.HIGH
        }

        binding.LayoutPriorityMedium.setOnClickListener {
            setPriority(binding.LayoutPriorityMedium)
            task.enumPriority = EnumPriority.MEDIUM
        }

        binding.LayoutPriorityLow.setOnClickListener {
            setPriority(binding.LayoutPriorityLow)
            task.enumPriority = EnumPriority.LOW
        }

        binding.cbNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.layoutHour.visibility = View.VISIBLE
                binding.notificationContainer.visibility = View.VISIBLE
            } else {
                binding.layoutHour.visibility = View.GONE
                binding.notificationContainer.visibility = View.GONE
            }
        }

        binding.LayoutSimple.setOnClickListener { setNotification(binding.LayoutSimple) }
        binding.LayoutSchedule.setOnClickListener { setNotification(binding.LayoutSchedule) }
        binding.LayoutNotificationFullScreen.setOnClickListener { setNotification(binding.LayoutNotificationFullScreen) }


        binding.btnSave.setOnClickListener {
            if (validateData()) {
                task.title = binding.edtTile.text.toString()
                task.description = binding.edtDescription.text.toString()
                task.date = selectedDate!!.toEpochDay()

                if(edit)
                    DbHelper.getInstance(requireContext()).updateTask(task)
                else
                    DbHelper.getInstance(requireContext()).insertTask(task)

                Navigate.navigateFragment(requireActivity(), TasksFragment.getInstance())
            }
        }

        binding.btnCancel.setOnClickListener {
            Navigate.navigateFragment(requireActivity(), TasksFragment.getInstance())
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initTask()
    }

    fun initTask() {
        try {
            task = requireArguments().getSerializable("task") as Task
            binding.edtTile.setText(task.title)
            binding.edtDescription.setText(task.description)
            selectedDate = LocalDate.ofEpochDay(task.date)
            binding.txtDate.setText(converter.getLocalData(task.date))
            when (task.enumPriority) {
                EnumPriority.URGENT -> setPriority(binding.LayoutUrgent)
                EnumPriority.HIGH -> setPriority(binding.LayoutPriorityHigh)
                EnumPriority.MEDIUM -> setPriority(binding.LayoutPriorityMedium)
                EnumPriority.LOW -> setPriority(binding.LayoutPriorityLow)
            }

            when (task.notification) {
                EnumNotification.SIMPLE -> setNotification(binding.LayoutSimple)
                EnumNotification.SCHEDULE -> setNotification(binding.LayoutSchedule)
                EnumNotification.FULL_SCREAM -> setNotification(binding.LayoutNotificationFullScreen)
            }

            if (!task.repeat.getList().isEmpty()) {

            }

            edit = true
        } catch (e: Exception) {
            var selectedDateFronTasks = requireArguments().getLong("selectedDate")
            this.selectedDate = LocalDate.ofEpochDay(selectedDateFronTasks)

            e.printStackTrace()
            task = Task()
            binding.edtDescription.setText("")
            binding.edtTile.setText("")

            val dataFormatada = converter.getLocalData(selectedDateFronTasks)


            binding.txtDate.setText(dataFormatada)
            edit = false
        }
    }

    private fun setPriority(view: ConstraintLayout) {
        resetLayoutPriority()
        when (view.id) {
            binding.LayoutUrgent.id -> {
                binding.LayoutUrgent.setBackgroundResource(R.drawable.select_priority_urgent)
                task.enumPriority = EnumPriority.URGENT
            }

            binding.LayoutPriorityHigh.id -> {
                binding.LayoutPriorityHigh.setBackgroundResource(R.drawable.select_priority_high)
                task.enumPriority = EnumPriority.HIGH
            }

            binding.LayoutPriorityMedium.id -> {
                binding.LayoutPriorityMedium.setBackgroundResource(R.drawable.select_priority_medium)
                task.enumPriority = EnumPriority.MEDIUM
            }

            binding.LayoutPriorityLow.id -> {
                binding.LayoutPriorityLow.setBackgroundResource(R.drawable.select_priority_low)
                task.enumPriority = EnumPriority.LOW
            }
        }
    }

    private fun resetLayoutPriority() {
        binding.LayoutUrgent.setBackgroundResource(R.drawable.priority_default)
        binding.LayoutPriorityHigh.setBackgroundResource(R.drawable.priority_default)
        binding.LayoutPriorityMedium.setBackgroundResource(R.drawable.priority_default)
        binding.LayoutPriorityLow.setBackgroundResource(R.drawable.priority_default)
    }

    private fun resetLayoutNotification() {
        binding.LayoutSimple.setBackgroundResource(R.drawable.priority_default)
        binding.LayoutSchedule.setBackgroundResource(R.drawable.priority_default)
        binding.LayoutNotificationFullScreen.setBackgroundResource(R.drawable.priority_default)
        binding.tvSimple.setTextColor(Color.GRAY)
        binding.tvSchedule.setTextColor(Color.GRAY)
        binding.tvFullScreen.setTextColor(Color.GRAY)
    }

    private fun setNotification(view: ConstraintLayout) {
        resetLayoutNotification()
        when (view.id) {
            binding.LayoutSimple.id -> {
                binding.LayoutSimple.setBackgroundResource(R.drawable.schedule_selected)
                binding.tvSimple.setTextColor(Color.WHITE)
                task.notification = EnumNotification.SIMPLE
            }

            binding.LayoutSchedule.id -> {
                binding.LayoutSchedule.setBackgroundResource(R.drawable.schedule_selected)
                binding.tvSchedule.setTextColor(Color.WHITE)
                task.notification = EnumNotification.SCHEDULE
            }

            binding.LayoutNotificationFullScreen.id -> {
                binding.LayoutNotificationFullScreen.setBackgroundResource(R.drawable.schedule_selected)
                binding.tvFullScreen.setTextColor(Color.WHITE)
                task.notification = EnumNotification.FULL_SCREAM
            }
        }
    }

    private fun validateData(): Boolean {
        var validate = true
        if (binding.edtTile.text!!.isEmpty() || binding.edtTile.text!!.isBlank()) {
            binding.layoutTitle.isErrorEnabled = true
            validate = false
        }

        if (binding.edtDescription.text!!.isEmpty() || binding.edtDescription.text!!.isBlank()) {
            binding.layoutDescription.isErrorEnabled = true
            validate = false
        }

        return validate
    }

    private fun setupDatePicker() {

        val calendar = Calendar.getInstance()

        val openDatePicker = {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->

                    val localDate = LocalDate.of(
                        year,
                        month + 1,
                        dayOfMonth
                    )

                    selectedDate = localDate

                    val formatter = DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.SHORT)
                        .withLocale(Locale.getDefault())

                    binding.txtDate.setText(localDate.format(formatter))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.layoutDate.setOnClickListener { openDatePicker() }
    }
}

