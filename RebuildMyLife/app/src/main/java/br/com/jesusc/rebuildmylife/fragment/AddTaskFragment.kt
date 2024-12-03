package br.com.jesusc.rebuildmylife.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.jesusc.rebuildmylife.viewModel.AddTaskViewModel
import br.com.jesusc.rebuildmylife.R
import kotlin.reflect.KParameter

class AddTaskFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }
}

