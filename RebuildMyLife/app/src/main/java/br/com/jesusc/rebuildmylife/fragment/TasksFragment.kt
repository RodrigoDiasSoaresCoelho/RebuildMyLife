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
import br.com.jesusc.rebuildmylife.viewModel.TaskViewModel
import br.com.jesusc.rebuildmylife.viewModel.TaskViewModelFactory

class TasksFragment : Fragment() {
    lateinit var binding: FragmentTasksBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

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

//        binding.btnAddTask.setOnClickListener(v -> {
//
//        })
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

        // Observar as Tasks
        taskViewModel.tasks.observe(viewLifecycleOwner) { taskList ->
            taskAdapter = TaskAdapter(taskList) // Atualizar RecyclerView com as Tasks
            binding.recyclerTasks.adapter = taskAdapter
        }

        // Carregar as Tasks
        taskViewModel.loadTasks()
    }
}