package br.com.jesusc.rebuildmylife.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.jesusc.rebuildmylife.adapter.DateAdapter
import br.com.jesusc.rebuildmylife.adapter.TaskAdapter
import br.com.jesusc.rebuildmylife.databinding.FragmentTasksBinding
import br.com.jesusc.rebuildmylife.helper.DbHelper
import br.com.jesusc.rebuildmylife.helper.TaskDAO
import br.com.jesusc.rebuildmylife.menager.DateUiManager
import br.com.jesusc.rebuildmylife.model.Task
import br.com.jesusc.rebuildmylife.model.UiDate
import br.com.jesusc.rebuildmylife.util.CallbackDate
import br.com.jesusc.rebuildmylife.util.CallbackTask
import br.com.jesusc.rebuildmylife.util.Navigate
import br.com.jesusc.rebuildmylife.viewModel.TaskViewModel
import br.com.jesusc.rebuildmylife.viewModel.TaskViewModelFactory

class TasksFragment : Fragment(), CallbackDate {
    lateinit var binding: FragmentTasksBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private var callbackTask: CallbackTask

    private lateinit var adapter: DateAdapter
    private lateinit var dbHelper: DbHelper
    private lateinit var taskDAO: TaskDAO
    private val dateManager = DateUiManager()

    init{
        callbackTask = setCallback()
    }

    companion object {
        private val INSTANCE: TasksFragment by lazy {
            TasksFragment()
        }

        fun getInstance(): TasksFragment {
            return INSTANCE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)

        binding.recyclerTasks.layoutManager = LinearLayoutManager(requireContext())

        dbHelper = DbHelper.getInstance(requireContext())
        taskDAO = TaskDAO.getInstance(dbHelper)
        val factory = TaskViewModelFactory(taskDAO)
        taskViewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]

        taskViewModel.tasks.observe(viewLifecycleOwner) { taskList ->
            taskAdapter = TaskAdapter(taskList, setCallback())
            binding.recyclerTasks.adapter = taskAdapter
//            taskAdapter.submitList(taskList)
        }

        binding.btnAddTask.setOnClickListener({
            Navigate.navigateFragment(requireActivity(), AddTaskFragment.getInstance())
        })

        val list = dateManager.getDatesForUi()
        adapter = DateAdapter(list, this)


        binding.recyclerDay.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        adapter.submitList(dateManager.getDatesForUi())
        val uiDate = adapter.selectTodayIfExists()
        if(uiDate != null)
        taskViewModel.loadTasks(uiDate)

        binding.recyclerDay.post {
            val position = adapter.getSelectedPosition()
            if (position != RecyclerView.NO_POSITION) {
                (binding.recyclerDay.layoutManager as LinearLayoutManager)
                    .scrollToPositionWithOffset(position, binding.recyclerDay.width / 2)
            }
        }

        binding.recyclerDay.adapter = adapter

        binding.txtMonthAndYear.text = dateManager.getCurrentMonthLabel()
//
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar o RecyclerView



    }

    fun setCallback(): CallbackTask{
        return object : CallbackTask {
            override fun taskChecked(task: Task) {
                taskViewModel.updateTask(task)
                taskViewModel.taskChecked(callbackTask)
            }

            override fun taskUnChecked(task: Task) {
                TODO("Not yet implemented")
            }

            override fun taskDeleted(task: Task) {
                TODO("Not yet implemented")
            }

            override fun taskUpdated(task: Task) {
                TODO("Not yet implemented")
            }

            override fun notifyDataSetChanged() {
                taskAdapter.notifyDataSetChanged()
            }

            override fun taskClicked(task: Task) {
                val bundle = Bundle().apply {
                    putSerializable("task", task)
                }
                Navigate.navigateFragment(requireActivity(),AddTaskFragment.getInstance(),bundle)
            }

        }
    }

    override fun dateSelected(uiDate: UiDate) {
        dateManager.selectDate(uiDate.day.toInt())
        (binding.recyclerDay.layoutManager as LinearLayoutManager)
            .scrollToPositionWithOffset(adapter.getSelectedPosition(), binding.recyclerDay.width / 2)
        taskViewModel.loadTasks(uiDate)
    }
}