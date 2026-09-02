package br.com.jesusc.rebuildmylife.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.jesusc.rebuildmylife.R
import br.com.jesusc.rebuildmylife.adapter.DateAdapter
import br.com.jesusc.rebuildmylife.alarm.AlarmPermissionHelper
import br.com.jesusc.rebuildmylife.databinding.ActivityMainBinding
import br.com.jesusc.rebuildmylife.fragment.AddTaskFragment
import br.com.jesusc.rebuildmylife.fragment.SplashFragment
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

        Navigate.navigateFragment(this, SplashFragment())
    }

    private fun requestAlarmPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS
                    ),
                    REQUEST_NOTIFICATION
                )
            }
        }

        if (
            !AlarmPermissionHelper
                .canScheduleExactAlarms(this)
        ) {

            AlarmPermissionHelper
                .openExactAlarmSettings(this)
        }
    }

    companion object {

        private const val REQUEST_NOTIFICATION = 1001
    }
}