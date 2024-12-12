package br.com.jesusc.rebuildmylife.fragment

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
import br.com.jesusc.rebuildmylife.enums.EnumPriority
import br.com.jesusc.rebuildmylife.enums.EnumSchedule
import br.com.jesusc.rebuildmylife.model.Task

class AddTaskFragment : Fragment() {

 private lateinit var binding:FragmentAddTaskBinding
 private lateinit var task:Task

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
        task = Task()

        binding.LayoutUrgent.setOnClickListener {
            setPriority(binding.LayoutUrgent)
        }

        binding.LayoutPriorityHigh.setOnClickListener {
            setPriority(binding.LayoutPriorityHigh)
        }

        binding.LayoutPriorityMedium.setOnClickListener {
            setPriority(binding.LayoutPriorityMedium)
        }

        binding.LayoutPriorityLow.setOnClickListener {
            setPriority(binding.LayoutPriorityLow)
        }

        binding.repeatCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.repeatContainer.visibility = View.VISIBLE
            } else {
                binding.repeatContainer.visibility = View.GONE
            }
        }

        binding.sundayLayout.setOnClickListener{setSchedule(binding.sundayLayout)}
        binding.LayoutMonday.setOnClickListener{setSchedule(binding.LayoutMonday)}
        binding.LayoutTue.setOnClickListener{setSchedule(binding.LayoutTue)}
        binding.LayoutWednesday.setOnClickListener{setSchedule(binding.LayoutWednesday)}
        binding.LayoutThursday.setOnClickListener{setSchedule(binding.LayoutThursday)}
        binding.LayoutFriday.setOnClickListener{setSchedule(binding.LayoutFriday)}
        binding.LayoutSaturday.setOnClickListener{setSchedule(binding.LayoutSaturday)}



        return binding.root
    }

    private fun setPriority(view: ConstraintLayout){
        resetLayoutPriority()
        when(view.id){
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

    private fun resetLayoutPriority(){
        binding.LayoutUrgent.setBackgroundResource(R.drawable.priority_default)
        binding.LayoutPriorityHigh.setBackgroundResource(R.drawable.priority_default)
        binding.LayoutPriorityMedium.setBackgroundResource(R.drawable.priority_default)
        binding.LayoutPriorityLow.setBackgroundResource(R.drawable.priority_default)
    }

    private fun setSchedule(view: ConstraintLayout){
        when(view.id){
            binding.sundayLayout.id -> {
                if(!task.repeat.getList().contains(EnumSchedule.Sunday)) {
                    task.repeat.addSchedule(EnumSchedule.Sunday)
                    binding.sundayLayout.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvSun.setTextColor(Color.WHITE)
                }else{
                    task.repeat.removeSchedule(EnumSchedule.Sunday)
                    binding.sundayLayout.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvSun.setTextColor(Color.GRAY)
                }
            }
            binding.LayoutMonday.id -> {
                if(!task.repeat.getList().contains(EnumSchedule.Monday)) {
                    task.repeat.addSchedule(EnumSchedule.Monday)
                    binding.LayoutMonday.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvMon.setTextColor(Color.WHITE)
                }else{
                    task.repeat.removeSchedule(EnumSchedule.Monday)
                    binding.LayoutMonday.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvMon.setTextColor(Color.GRAY)
                }
            }
            binding.LayoutTue.id -> {
                if(!task.repeat.getList().contains(EnumSchedule.Tuesday)) {
                    task.repeat.addSchedule(EnumSchedule.Tuesday)
                    binding.LayoutTue.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvTue.setTextColor(Color.WHITE)
                }else{
                    task.repeat.removeSchedule(EnumSchedule.Tuesday)
                    binding.LayoutTue.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvTue.setTextColor(Color.GRAY)
                }
            }
            binding.LayoutWednesday.id -> {
                if(!task.repeat.getList().contains(EnumSchedule.Wednesday)) {
                    task.repeat.addSchedule(EnumSchedule.Wednesday)
                    binding.LayoutWednesday.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvWed.setTextColor(Color.WHITE)
                }else{
                    task.repeat.removeSchedule(EnumSchedule.Wednesday)
                    binding.LayoutWednesday.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvWed.setTextColor(Color.GRAY)
                }
            }
            binding.LayoutThursday.id -> {
                if(!task.repeat.getList().contains(EnumSchedule.Thursday)) {
                    task.repeat.addSchedule(EnumSchedule.Thursday)
                    binding.LayoutThursday.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvThu.setTextColor(Color.WHITE)
                }else{
                    task.repeat.removeSchedule(EnumSchedule.Thursday)
                    binding.LayoutThursday.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvThu.setTextColor(Color.GRAY)
                }
            }
            binding.LayoutFriday.id -> {
                if(!task.repeat.getList().contains(EnumSchedule.Friday)) {
                    task.repeat.addSchedule(EnumSchedule.Friday)
                    binding.LayoutFriday.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvFri.setTextColor(Color.WHITE)
                }else{
                    task.repeat.removeSchedule(EnumSchedule.Friday)
                    binding.LayoutFriday.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvFri.setTextColor(Color.GRAY)
                }
            }
            binding.LayoutSaturday.id -> {
                if(!task.repeat.getList().contains(EnumSchedule.Saturday)) {
                    task.repeat.addSchedule(EnumSchedule.Saturday)
                    binding.LayoutSaturday.setBackgroundResource(R.drawable.schedule_selected)
                    binding.tvSat.setTextColor(Color.WHITE)
                }else{
                    task.repeat.removeSchedule(EnumSchedule.Saturday)
                    binding.LayoutSaturday.setBackgroundResource(R.drawable.schedule_default)
                    binding.tvSat.setTextColor(Color.GRAY)
                }
            }
        }
    }
}

