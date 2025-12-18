package br.com.jesusc.rebuildmylife.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jesusc.rebuildmylife.adapter.TaskAdapter
import br.com.jesusc.rebuildmylife.databinding.FragmentTasksBinding
import br.com.jesusc.rebuildmylife.helper.DbHelper
import br.com.jesusc.rebuildmylife.helper.TaskDAO
import br.com.jesusc.rebuildmylife.model.Task
import br.com.jesusc.rebuildmylife.util.CallbackTask
import br.com.jesusc.rebuildmylife.util.Navigate
import br.com.jesusc.rebuildmylife.viewModel.TaskViewModel
import br.com.jesusc.rebuildmylife.viewModel.TaskViewModelFactory

class TasksFragment : Fragment() {
    lateinit var binding: FragmentTasksBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private var callbackTask: CallbackTask

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

        binding.btnAddTask.setOnClickListener({
            Navigate.navigateFragment(requireActivity(), AddTaskFragment.getInstance())
        })
//
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar o RecyclerView


        binding.recyclerTasks.layoutManager = LinearLayoutManager(requireContext())

        // Obter DbHelper e TaskDAO
        val dbHelper = DbHelper.getInstance(requireContext())
        val taskDAO = TaskDAO.getInstance(dbHelper)

        // Configurar o ViewModel
        val factory = TaskViewModelFactory(taskDAO)
        taskViewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]
// Carregar as Tasks
        taskViewModel.loadTasks()
        // Observar as Tasks
        taskViewModel.tasks.observe(viewLifecycleOwner) { taskList ->
            taskAdapter = TaskAdapter(taskList, setCallback())
            binding.recyclerTasks.adapter = taskAdapter
        }
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
}