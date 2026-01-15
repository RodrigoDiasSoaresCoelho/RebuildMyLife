package br.com.jesusc.rebuildmylife.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.jesusc.rebuildmylife.R
import br.com.jesusc.rebuildmylife.adapter.DateAdapter
import br.com.jesusc.rebuildmylife.databinding.ActivityMainBinding
import br.com.jesusc.rebuildmylife.fragment.AddTaskFragment
import br.com.jesusc.rebuildmylife.fragment.TasksFragment
import br.com.jesusc.rebuildmylife.menager.DateUiManager
import br.com.jesusc.rebuildmylife.model.UiDate
import br.com.jesusc.rebuildmylife.util.CallbackDate
import br.com.jesusc.rebuildmylife.util.Navigate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



//        Navigate.navigateFragment(this, AddTaskFragment.getInstance())
        Navigate.navigateFragment(this, TasksFragment.getInstance())
    }
}