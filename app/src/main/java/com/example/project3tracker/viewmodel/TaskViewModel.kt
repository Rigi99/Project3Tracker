package com.example.project3tracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3tracker.App
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.api.model.Task
import com.example.project3tracker.api.model.TaskResponse
import com.example.project3tracker.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val TAG: String = javaClass.simpleName
    }

    var tasks: MutableLiveData<List<TaskResponse>> = MutableLiveData()
    lateinit var selectedTask: Task

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getTasks(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d(TAG, "Get tasks response: ${response.body()}")

                    val tasksList = response.body()
                    tasksList?.let {
                        tasks.value = tasksList
                    }
                } else {
                    Log.d(TAG, "Get tasks error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                Log.d(TAG, "TasksViewModel - getTasks() failed with exception: ${e.message}")
            }
        }
    }
}