package com.example.project3tracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.api.model.UpdateProfileRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {
    companion object {
        private val TAG: String = javaClass.simpleName
    }

    var isSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    fun updateUser(
        lastName: String,
        firstName: String,
        location: String,
        phoneNumber: String,
        imageUrl: String
    ) {
        val requestBody = UpdateProfileRequest(
            lastName,
            firstName,
            location,
            phoneNumber,
            imageUrl
        )
        viewModelScope.launch {
            executeCreateTask(requestBody)
        }
    }

    private suspend fun executeCreateTask(requestBody: UpdateProfileRequest) {
        try {
            val response = withContext(Dispatchers.IO) {
                repository.updateUser(requestBody)
            }

            if (response.isSuccessful) {
                Log.d(TAG, "Creating task response: ${response.body()}")

                val responseToken = response.body()?.message
                responseToken?.let {
                    isSuccessful.value = true
                }
            } else {
                Log.d(TAG, "Update user error response: ${response.message()}")
                isSuccessful.value = false
            }
        } catch (e: Exception) {
            Log.d(TAG, "NewTaskViewModel - createTask() failed with exception: ${e.message}")
            isSuccessful.value = false
        }
    }
}