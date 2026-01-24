package br.com.jesusc.rebuildmylife.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.jesusc.rebuildmylife.R
import br.com.jesusc.rebuildmylife.databinding.FragmentRepeatBinding
import br.com.jesusc.rebuildmylife.enums.EnumSchedule

class RepeatFragment : Fragment() {
    private var param1: String? = null
    private lateinit var binding: FragmentRepeatBinding


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

        binding.sundayLayout.setOnClickListener { setSchedule(binding.sundayLayout) }
        binding.LayoutMonday.setOnClickListener { setSchedule(binding.LayoutMonday) }
        binding.LayoutTue.setOnClickListener { setSchedule(binding.LayoutTue) }
        binding.LayoutWednesday.setOnClickListener { setSchedule(binding.LayoutWednesday) }
        binding.LayoutThursday.setOnClickListener { setSchedule(binding.LayoutThursday) }
        binding.LayoutFriday.setOnClickListener { setSchedule(binding.LayoutFriday) }
        binding.LayoutSaturday.setOnClickListener { setSchedule(binding.LayoutSaturday) }

        binding.repeatContainer.visibility = View.VISIBLE

//        task.repeat.getList().forEach {
//            when (it) {
//                EnumSchedule.Sunday.value -> editSchedule(binding.sundayLayout)
//                EnumSchedule.Monday.value -> editSchedule(binding.LayoutMonday)
//                EnumSchedule.Tuesday.value -> editSchedule(binding.LayoutTue)
//                EnumSchedule.Wednesday.value -> editSchedule(binding.LayoutWednesday)
//                EnumSchedule.Thursday.value -> editSchedule(binding.LayoutThursday)
//                EnumSchedule.Friday.value -> editSchedule(binding.LayoutFriday)
//                EnumSchedule.Saturday.value -> editSchedule(binding.LayoutSaturday)
//            }
//        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RepeatFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun setSchedule(view: ConstraintLayout) {
//        when (view.id) {
//            binding.sundayLayout.id -> {
//                if (!task.repeat.getList().contains(EnumSchedule.Sunday.value)) {
//                    task.repeat.addSchedule(EnumSchedule.Sunday)
//                    binding.sundayLayout.setBackgroundResource(R.drawable.schedule_selected)
//                    binding.tvSun.setTextColor(Color.WHITE)
//                } else {
//                    task.repeat.removeSchedule(EnumSchedule.Sunday)
//                    binding.sundayLayout.setBackgroundResource(R.drawable.schedule_default)
//                    binding.tvSun.setTextColor(Color.GRAY)
//                }
//            }
//
//            binding.LayoutMonday.id -> {
//                if (!task.repeat.getList().contains(EnumSchedule.Monday.value)) {
//                    task.repeat.addSchedule(EnumSchedule.Monday)
//                    binding.LayoutMonday.setBackgroundResource(R.drawable.schedule_selected)
//                    binding.tvMon.setTextColor(Color.WHITE)
//                } else {
//                    task.repeat.removeSchedule(EnumSchedule.Monday)
//                    binding.LayoutMonday.setBackgroundResource(R.drawable.schedule_default)
//                    binding.tvMon.setTextColor(Color.GRAY)
//                }
//            }
//
//            binding.LayoutTue.id -> {
//                if (!task.repeat.getList().contains(EnumSchedule.Tuesday.value)) {
//                    task.repeat.addSchedule(EnumSchedule.Tuesday)
//                    binding.LayoutTue.setBackgroundResource(R.drawable.schedule_selected)
//                    binding.tvTue.setTextColor(Color.WHITE)
//                } else {
//                    task.repeat.removeSchedule(EnumSchedule.Tuesday)
//                    binding.LayoutTue.setBackgroundResource(R.drawable.schedule_default)
//                    binding.tvTue.setTextColor(Color.GRAY)
//                }
//            }
//
//            binding.LayoutWednesday.id -> {
//                if (!task.repeat.getList().contains(EnumSchedule.Wednesday.value)) {
//                    task.repeat.addSchedule(EnumSchedule.Wednesday)
//                    binding.LayoutWednesday.setBackgroundResource(R.drawable.schedule_selected)
//                    binding.tvWed.setTextColor(Color.WHITE)
//                } else {
//                    task.repeat.removeSchedule(EnumSchedule.Wednesday)
//                    binding.LayoutWednesday.setBackgroundResource(R.drawable.schedule_default)
//                    binding.tvWed.setTextColor(Color.GRAY)
//                }
//            }
//
//            binding.LayoutThursday.id -> {
//                if (!task.repeat.getList().contains(EnumSchedule.Thursday.value)) {
//                    task.repeat.addSchedule(EnumSchedule.Thursday)
//                    binding.LayoutThursday.setBackgroundResource(R.drawable.schedule_selected)
//                    binding.tvThu.setTextColor(Color.WHITE)
//                } else {
//                    task.repeat.removeSchedule(EnumSchedule.Thursday)
//                    binding.LayoutThursday.setBackgroundResource(R.drawable.schedule_default)
//                    binding.tvThu.setTextColor(Color.GRAY)
//                }
//            }
//
//            binding.LayoutFriday.id -> {
//                if (!task.repeat.getList().contains(EnumSchedule.Friday.value)) {
//                    task.repeat.addSchedule(EnumSchedule.Friday)
//                    binding.LayoutFriday.setBackgroundResource(R.drawable.schedule_selected)
//                    binding.tvFri.setTextColor(Color.WHITE)
//                } else {
//                    task.repeat.removeSchedule(EnumSchedule.Friday)
//                    binding.LayoutFriday.setBackgroundResource(R.drawable.schedule_default)
//                    binding.tvFri.setTextColor(Color.GRAY)
//                }
//            }
//
//            binding.LayoutSaturday.id -> {
//                if (!task.repeat.getList().contains(EnumSchedule.Saturday.value)) {
//                    task.repeat.addSchedule(EnumSchedule.Saturday)
//                    binding.LayoutSaturday.setBackgroundResource(R.drawable.schedule_selected)
//                    binding.tvSat.setTextColor(Color.WHITE)
//                } else {
//                    task.repeat.removeSchedule(EnumSchedule.Saturday)
//                    binding.LayoutSaturday.setBackgroundResource(R.drawable.schedule_default)
//                    binding.tvSat.setTextColor(Color.GRAY)
//                }
//            }
//        }
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
}