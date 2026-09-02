package br.com.jesusc.rebuildmylife.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import br.com.jesusc.rebuildmylife.databinding.FragmentSplashBinding
import br.com.jesusc.rebuildmylife.util.Navigate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds


class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        animateLogo()

        return binding.root
    }

    private fun animateLogo() {
        val text = "VitaDiem"

        lifecycleScope.launch {

            binding.txtLogo.text = ""

            for (i in text.indices) {
                binding.txtLogo.text = text.substring(0, i + 1) + "|"
                delay(150.milliseconds)
            }

            binding.txtLogo.text = text
            animateCheckBox()
            delay(700.milliseconds)

            Navigate.navigateFragment(requireActivity(), TasksFragment.getInstance())
        }
    }

    private fun animateCheckBox() {

        binding.checkLogo.apply {

            visibility = View.VISIBLE

            alpha = 0f
            scaleX = 0f
            scaleY = 0f

            animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(350)
                .setInterpolator(OvershootInterpolator())
                .withEndAction {

                    isChecked = true
                }
                .start()
        }
    }
}