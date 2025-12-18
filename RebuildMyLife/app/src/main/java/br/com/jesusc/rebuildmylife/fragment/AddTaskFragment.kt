package br.com.jesusc.rebuildmylife.fragment

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.jesusc.rebuildmylife.viewModel.AddTaskViewModel
import br.com.jesusc.rebuildmylife.R
import br.com.jesusc.rebuildmylife.databinding.FragmentAddTaskBinding
import br.com.jesusc.rebuildmylife.enums.EnumNotification
import br.com.jesusc.rebuildmylife.enums.EnumPriority
import br.com.jesusc.rebuildmylife.enums.EnumSchedule
import br.com.jesusc.rebuildmylife.helper.DbHelper
import br.com.jesusc.rebuildmylife.model.Notification
import br.com.jesusc.rebuildmylife.model.Task
import br.com.jesusc.rebuildmylife.util.Navigate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var task: Task
    private lateinit var notification: Notification
    private var edit = false

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

        notification = Notification()

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

        binding.repeatCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.repeatContainer.visibility = View.VISIBLE
            } else {
                binding.repeatContainer.visibility = View.GONE
            }

        }

        binding.sundayLayout.setOnClickListener { setSchedule(binding.sundayLayout) }
        binding.LayoutMonday.setOnClickListener { setSchedule(binding.LayoutMonday) }
        binding.LayoutTue.setOnClickListener { setSchedule(binding.LayoutTue) }
        binding.LayoutWednesday.setOnClickListener { setSchedule(binding.LayoutWednesday) }
        binding.LayoutThursday.setOnClickListener { setSchedule(binding.LayoutThursday) }
        binding.LayoutFriday.setOnClickListener { setSchedule(binding.LayoutFriday) }
        binding.LayoutSaturday.setOnClickListener { setSchedule(binding.LayoutSaturday) }

        binding.LayoutSimple.setOnClickListener { setNotification(binding.LayoutSimple) }
        binding.LayoutSchedule.setOnClickListener { setNotification(binding.LayoutSchedule) }
        binding.LayoutNotificationFullScreen.setOnClickListener { setNotification(binding.LayoutNotificationFullScreen) }


        binding.btnSave.setOnClickListener {
            if (validateData()) {
                task.title = binding.edtTile.text.toString()
                task.description = binding.edtDescription.text.toString()
                task.date = binding.edtDate.text.toString()

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
            binding.edtDate.setText(task.date)
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
                binding.repeatCheckBox.isChecked = true
                binding.repeatContainer.visibility = View.VISIBLE

                task.repeat.getList().forEach {
                    when (it) {
                        EnumSchedule.Monday -> editSchedule(binding.LayoutMonday)
                        EnumSchedule.Tuesday -> editSchedule(binding.LayoutTue)
                        EnumSchedule.Wednesday -> editSchedule(binding.LayoutWednesday)
                        EnumSchedule.Thursday -> editSchedule(binding.LayoutThursday)
                        EnumSchedule.Friday -> editSchedule(binding.LayoutFriday)
                        EnumSchedule.Saturday -> editSchedule(binding.LayoutSaturday)
                        EnumSchedule.Sunday -> editSchedule(binding.sundayLayout)
                    }
                }
            }

            edit = true
        } catch (e: Exception) {
            e.printStackTrace()
            task = Task()
            binding.edtDescription.setText("")
            binding.edtTile.setText("")

            val dataFormatada = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

            binding.edtDate.setText(dataFormatada)
            edit = false
        }
    }

    private fun setPriority(view: ConstraintLayout) {
        resetLayoutPriority()
        when (view.id) {
            binding.LayoutUrgent.id -> {
                binding.LayoutUrgent.setBackgroundResource(R.drawable.priority_urgent)
                task.enumPriority = EnumPriority.URGENT
            }

            binding.LayoutPriorityHigh.id -> {
                binding.LayoutPriorityHigh.setBackgroundResource(R.drawable.priority_high)
                task.enumPriority = EnumPriority.HIGH
            }

            binding.LayoutPriorityMedium.id -> {
                binding.LayoutPriorityMedium.setBackgroundResource(R.drawable.priority_medium)
                task.enumPriority = EnumPriority.MEDIUM
            }

            binding.LayoutPriorityLow.id -> {
                binding.LayoutPriorityLow.setBackgroundResource(R.drawable.priority_low)
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

    private fun setSchedule(view: ConstraintLayout) {
        when (view.id) {
            binding.sundayLayout.id -> {
                if (!task.repeat.getList().contains(EnumSchedule.Sunday)) {
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
                if (!task.repeat.getList().contains(EnumSchedule.Monday)) {
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
                if (!task.repeat.getList().contains(EnumSchedule.Tuesday)) {
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
                if (!task.repeat.getList().contains(EnumSchedule.Wednesday)) {
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
                if (!task.repeat.getList().contains(EnumSchedule.Thursday)) {
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
                if (!task.repeat.getList().contains(EnumSchedule.Friday)) {
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
                if (!task.repeat.getList().contains(EnumSchedule.Saturday)) {
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
                notification.enumNotification = EnumNotification.SIMPLE
            }

            binding.LayoutSchedule.id -> {
                binding.LayoutSchedule.setBackgroundResource(R.drawable.schedule_selected)
                binding.tvSchedule.setTextColor(Color.WHITE)
                notification.enumNotification = EnumNotification.SCHEDULE
            }

            binding.LayoutNotificationFullScreen.id -> {
                binding.LayoutNotificationFullScreen.setBackgroundResource(R.drawable.schedule_selected)
                binding.tvFullScreen.setTextColor(Color.WHITE)
                notification.enumNotification = EnumNotification.FULL_SCREAM
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
}

