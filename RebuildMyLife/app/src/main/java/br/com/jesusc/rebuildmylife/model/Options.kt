package br.com.jesusc.rebuildmylife.model

import br.com.jesusc.rebuildmylife.databinding.FragmentAddTaskBinding
import br.com.jesusc.rebuildmylife.fragment.AddTaskFragment

class Options {

    companion object {
        private val INSTANCE: Options by lazy {
            Options()
        }

        fun getInstance(): Options {
            return INSTANCE
        }
    }

}