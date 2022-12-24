package com.example.project3tracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3tracker.App
import com.example.project3tracker.api.BackendConstants
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.api.model.CreateTaskRequestBody
import com.example.project3tracker.manager.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewTaskViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val TAG: String = javaClass.simpleName
    }

    var isSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    fun createTask(
        title: String,
        description: String,
        assignedToUserId: Int,
        priority: Int,
        deadline: Long,
        departmentId: Int,
        status: Int
    ) {
        val requestBody = CreateTaskRequestBody(
            title,
            description,
            assignedToUserId,
            priority,
            deadline,
            departmentId,
            status
        )
        val token: String? = App.sharedPreferences.getStringValue(
            SharedPreferencesManager.KEY_TOKEN,
            "Empty token!"
        )
        viewModelScope.launch {
            if (token != null) {
                executeCreateTask(token, requestBody)
            }
        }
    }

    private suspend fun executeCreateTask(token: String, requestBody: CreateTaskRequestBody) {
        try {
            Log.d("XXX", token)
            val response = withContext(Dispatchers.IO) {
                repository.createTask(token, requestBody)
            }
            Log.d("XXX", response.toString())
            if (response.isSuccessful) {
                Log.d(TAG, "Creating task response: ${response.body()}")

                val responseToken = response.body()?.message
                responseToken?.let {
                    isSuccessful.value = true
                }
            } else {
                Log.d("XXX", "Add task error response: ${response.errorBody().toString()}")
                isSuccessful.value = false
            }
        } catch (e: Exception) {
            Log.d(TAG, "NewTaskViewModel - createTask() failed with exception: ${e.message}")
            isSuccessful.value = false
        }
    }
}