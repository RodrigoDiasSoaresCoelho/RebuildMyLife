package br.com.jesusc.rebuildmylife.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.jesusc.rebuildmylife.R
import br.com.jesusc.rebuildmylife.viewModel.TasksViewModel

class TasksFragment : Fragment() {

    companion object {
        fun newInstance() = TasksFragment()
    }

    private val viewModel: TasksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.fragment_tasks, container, false)



        return view
    }
}