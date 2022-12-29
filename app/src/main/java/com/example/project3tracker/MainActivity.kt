package com.example.project3tracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.project3tracker.databinding.ActivityMainBinding
import com.example.project3tracker.manager.SharedPreferencesManager

class MainActivity : AppCompatActivity() {

    private val TAG: String = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.tasks -> findNavController(R.id.nav_host_fragment).navigate(R.id.taskListFragment)
                R.id.settings -> findNavController(R.id.nav_host_fragment).navigate(R.id.profileFragment)
                R.id.newTask -> findNavController(R.id.nav_host_fragment).navigate(R.id.newTaskFragment)
                R.id.departments -> findNavController(R.id.nav_host_fragment).navigate(R.id.departmentsFragment)
                else -> {}
            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called!")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called!")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called!")
    }

    override fun onResume() {
        super.onResume()
        if (App.sharedPreferences.getStringValue(SharedPreferencesManager.KEY_TOKEN, "") != "") {
            if (App.sharedPreferences.getLongValue(
                    SharedPreferencesManager.KEY_TOKEN_DEADLINE,
                    System.currentTimeMillis()
                ) != System.currentTimeMillis()
            ) {
                findNavController(R.id.nav_host_fragment).navigate(R.id.taskListFragment)
            }
        }
    }
}