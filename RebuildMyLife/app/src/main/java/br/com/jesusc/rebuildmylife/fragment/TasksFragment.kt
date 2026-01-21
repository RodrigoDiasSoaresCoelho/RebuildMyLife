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
import br.com.jesusc.rebuildmylife.dialog.MonthYearPickerDialog
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
import java.time.YearMonth

class TasksFragment : Fragment(), CallbackDate {
    lateinit var binding: FragmentTasksBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private var callbackTask: CallbackTask

    private lateinit var adapter: DateAdapter
    private lateinit var dbHelper: DbHelper
    private lateinit var taskDAO: TaskDAO
    private val dateManager = DateUiManager()
    private lateinit var uiDate: UiDate

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
            if (taskList.isEmpty()){
                callbackTask.tasksEmpty()
            }else{
                callbackTask.tasksNotEmpty()
            }
        }

        binding.btnAddTask.setOnClickListener({
            val bundle = Bundle().apply {
                putLong("selectedDate", uiDate.date)
            }
            Navigate.navigateFragment(requireActivity(), AddTaskFragment.getInstance(),bundle)
        })

        val list = dateManager.getDatesForUi()
        adapter = DateAdapter(list, this)


        binding.recyclerDay.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        adapter.submitList(dateManager.getDatesForUi())

        uiDate = adapter.selectTodayIfExists()
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

        binding.txtMonthAndYear.setOnClickListener {
            MonthYearPickerDialog(
                requireContext(),
                dateManager.getCurrentYearMonth()
            ) { selectedYearMonth ->

                updateMonthAndDays(selectedYearMonth)
            }.show()
        }

        binding.selectMonthAndYear.setOnClickListener {
            MonthYearPickerDialog(
                requireContext(),
                dateManager.getCurrentYearMonth()
            ) { selectedYearMonth ->
                updateMonthAndDays(selectedYearMonth)
            }.show()
        }

        binding.txtToday.setOnClickListener {
            today()
        }

        return binding.root
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

            override fun tasksEmpty() {
                binding.lottieAnimationViewArrow.visibility = View.VISIBLE
            }

            override fun tasksNotEmpty() {
                binding.lottieAnimationViewArrow.visibility = View.GONE
            }

        }
    }

    override fun dateSelected(uiDate: UiDate) {
        this.uiDate = uiDate
        dateManager.selectDate(uiDate.date)
        (binding.recyclerDay.layoutManager as LinearLayoutManager)
            .scrollToPositionWithOffset(adapter.getSelectedPosition(), binding.recyclerDay.width / 2)
        taskViewModel.loadTasks(uiDate)
        binding.txtToday.visibility = View.VISIBLE
    }

    fun today() {
        dateManager.goToToday()

        adapter.submitList(dateManager.getDatesForUi())

        uiDate = adapter.selectTodayIfExists()
            ?: dateManager.getSelectedDate()!!

        binding.txtMonthAndYear.text = dateManager.getCurrentMonthLabel()

        taskViewModel.loadTasks(uiDate)

        binding.recyclerDay.post {
            val position = adapter.getSelectedPosition()
            if (position != RecyclerView.NO_POSITION) {
                (binding.recyclerDay.layoutManager as LinearLayoutManager)
                    .scrollToPositionWithOffset(position, binding.recyclerDay.width / 2)
            }
        }

        binding.txtToday.visibility = View.GONE
    }


    private fun updateMonthAndDays(yearMonth: YearMonth) {
        dateManager.setYearMonth(yearMonth)

        val days = dateManager.getDatesForUi()
        adapter.submitList(days)

        uiDate = days.firstOrNull()
            ?: return

        adapter.selectPosition(0)

        binding.txtMonthAndYear.text = dateManager.getCurrentMonthLabel()

        binding.recyclerDay.post {
            (binding.recyclerDay.layoutManager as LinearLayoutManager)
                .scrollToPositionWithOffset(0, 0)
        }

        binding.txtToday.visibility = View.VISIBLE

        taskViewModel.loadTasks(uiDate)
    }

}