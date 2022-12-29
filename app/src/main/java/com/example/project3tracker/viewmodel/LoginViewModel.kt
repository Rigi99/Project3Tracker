package com.example.project3tracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3tracker.App
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.api.model.LoginRequestBody
import com.example.project3tracker.manager.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val TAG: String = javaClass.simpleName
    }

    var isSuccessful: MutableLiveData<Boolean> = MutableLiveData()

    fun login(username: String, password: String) {
        val requestBody = LoginRequestBody(username, password)
        viewModelScope.launch {
            executeLogin(requestBody)
        }
    }

    private suspend fun executeLogin(requestBody: LoginRequestBody) {
        try {
            val response = withContext(Dispatchers.IO) {
                repository.login(requestBody)
            }

            if (response.isSuccessful) {
                Log.d(TAG, "Login response: ${response.body()}")

                val responseToken = response.body()
                responseToken?.let {
                    App.sharedPreferences.putStringValue(SharedPreferencesManager.KEY_TOKEN, it.token)
                    App.sharedPreferences.putLongValue(SharedPreferencesManager.KEY_TOKEN_DEADLINE, it.deadline)
                    isSuccessful.value = true
                }
            } else {
                Log.d(TAG, "Login error response: ${response.message()}")
                isSuccessful.value = false
            }
        } catch (e: Exception) {
            Log.d(TAG, "LoginViewModel - login() failed with exception: ${e.message}")
            isSuccessful.value = false
        }
    }
}