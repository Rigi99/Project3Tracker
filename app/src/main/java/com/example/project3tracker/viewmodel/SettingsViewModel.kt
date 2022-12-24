package com.example.project3tracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3tracker.App
import com.example.project3tracker.api.BackendConstants
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.api.model.UpdateProfileRequest
import com.example.project3tracker.manager.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val TAG: String = javaClass.simpleName
    }

    var isSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    fun updateUser(
        firstName: String,
        lastName: String,
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
        val token = App.sharedPreferences.getStringValue(
            SharedPreferencesManager.KEY_TOKEN,
            "Empty token!"
        )
        viewModelScope.launch {
            if (token != null) {
                executeUpdateUser(token, requestBody)
            }
        }
    }

    private suspend fun executeUpdateUser(token: String, requestBody: UpdateProfileRequest) {
        try {
            val response = withContext(Dispatchers.IO) {
                repository.updateUser(token, requestBody)
            }

            if (response.isSuccessful) {
                Log.d(TAG, "Update user response: ${response.body()}")

                val responseToken = response.body()?.message
                responseToken?.let {
                    isSuccessful.value = true
                }
            } else {
                Log.d(TAG, "Update user error response: ${response.message()}")
                isSuccessful.value = false
            }
        } catch (e: Exception) {
            Log.d(TAG, "SettingsViewModel - updateUser() failed with exception: ${e.message}")
            isSuccessful.value = false
        }
    }
}