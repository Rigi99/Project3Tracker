package com.example.project3tracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3tracker.App
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.api.model.CreateTaskRequestBody
import com.example.project3tracker.api.model.LoginRequestBody
import com.example.project3tracker.manager.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Time

class NewTaskViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {
    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var isSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    fun createTask(
        description: String,
        assignedToUserId: Int,
        priority: Int,
        deadline: Time,
        departmentId: Int,
        status: Int
    ) {
        val requestBody = CreateTaskRequestBody(
            description,
            assignedToUserId,
            priority,
            deadline,
            departmentId,
            status
        )
        viewModelScope.launch {
            executeCreateTask(requestBody)
        }
    }

    private suspend fun executeCreateTask(requestBody: CreateTaskRequestBody) {
        try {
            val response = withContext(Dispatchers.IO) {
                repository.createTask(requestBody)
            }

            if (response.isSuccessful) {
                Log.d(TAG, "Creating task response: ${response.body()}")

                val responseToken = response.body()?.message
                responseToken?.let {
                    isSuccessful.value = true
                }
            } else {
                Log.d(TAG, "Login error response: ${response.message()}")
                isSuccessful.value = false
            }
        } catch (e: Exception) {
            Log.d(TAG, "NewTaskViewModel - createTask() failed with exception: ${e.message}")
            isSuccessful.value = false
        }
    }
}