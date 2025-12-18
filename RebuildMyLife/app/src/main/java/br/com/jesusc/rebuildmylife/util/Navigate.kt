package br.com.jesusc.rebuildmylife.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import br.com.jesusc.rebuildmylife.R

object Navigate {
    fun navigateFragment(activity: FragmentActivity, fragment: Fragment) {
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commitNow()
    }

    fun navigateFragment(activity: FragmentActivity, fragment: Fragment, bundle: Bundle) {
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment.javaClass, bundle,null)
        transaction.commitNow()
    }

    fun navigateToActivity(context: Context, c: Class<*>?) {
        val intent = Intent(context, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
