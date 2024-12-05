package br.com.jesusc.rebuildmylife.fragment

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

class AddTaskFragment : Fragment() {
 lateinit var binding:FragmentAddTaskBinding

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
        binding = FragmentAddTaskBinding.inflate(layoutInflater)

        binding.LayoutUrgent.setOnClickListener {
            setLayoutPriority(binding.LayoutUrgent)
        }

        binding.LayoutPriorityHigh.setOnClickListener {
            setLayoutPriority(binding.LayoutPriorityHigh)
        }

        binding.LayoutPriorityMedium.setOnClickListener {
            setLayoutPriority(binding.LayoutPriorityMedium)
        }

        binding.LayoutPriorityLow.setOnClickListener {
            setLayoutPriority(binding.LayoutPriorityLow)
        }


        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    fun setLayoutPriority(view: ConstraintLayout){
        resetLayoutPriority()
        when(view.id){
            binding.LayoutUrgent.id -> {
                binding.LayoutUrgent.setBackgroundResource(R.drawable.priority_urgent)
            }
            binding.LayoutPriorityHigh.id -> {
                binding.LayoutPriorityHigh.setBackgroundResource(R.drawable.priority_high)
            }
            binding.LayoutPriorityMedium.id -> {
                binding.LayoutPriorityMedium.setBackgroundResource(R.drawable.priority_medium)
            }
            binding.LayoutPriorityLow.id -> {
                binding.LayoutPriorityLow.setBackgroundResource(R.drawable.priority_low)
            }
        }
    }

    fun resetLayoutPriority(){
        binding.LayoutUrgent.setBackgroundResource(R.drawable.priority_default)
        binding.LayoutPriorityHigh.setBackgroundResource(R.drawable.priority_default)
        binding.LayoutPriorityMedium.setBackgroundResource(R.drawable.priority_default)
        binding.LayoutPriorityLow.setBackgroundResource(R.drawable.priority_default)
    }
}

