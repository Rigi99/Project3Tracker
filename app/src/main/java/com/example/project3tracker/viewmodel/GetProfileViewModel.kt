package com.example.project3tracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3tracker.App
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.api.model.GetProfileResponse
import com.example.project3tracker.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

class GetProfileViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val TAG: String = javaClass.simpleName
    }

    var data: MutableLiveData<GetProfileResponse> = MutableLiveData()

    init {
        getUserData()
    }

    fun getUserData() {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getUserData(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d(TAG, "Get user data response: ${response.body()}")

                    val userData = response.body()
                    userData?.let {
                        data.value = userData
                    }
                } else {
                    Log.d(TAG, "Get user data response: ${response?.errorBody()}")
                }
            } catch (e: Exception) {
                Log.d(
                    TAG,
                    "GetProfileViewModel - getUserData() failed with exception: ${e.message}"
                )
            }
        }
    }
}