package br.com.jesusc.rebuildmylife.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.jesusc.rebuildmylife.R

class ToolbarFragment : Fragment() {

    companion object {
        fun newInstance() = ToolbarFragment()
    }

    private val viewModel: ToolbarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_toolbar, container, false)
    }
}