package com.example.project3tracker.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3tracker.App
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.api.model.Department
import com.example.project3tracker.api.model.GetDepartmentsResponse
import com.example.project3tracker.api.model.GetProfileResponse
import com.example.project3tracker.manager.SharedPreferencesManager
import kotlinx.coroutines.launch

class GetDepartmentsViewModel(private val repository: ThreeTrackerRepository) : ViewModel() {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val TAG: String = javaClass.simpleName
    }

    var departments: MutableLiveData<List<GetDepartmentsResponse>> = MutableLiveData()
    private var users: MutableLiveData<List<GetProfileResponse>> = MutableLiveData()
    lateinit var selectedDepartment: Department

    init {
        getUsers()
    }

    private fun getDepartments() {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getDepartments(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d(TAG, "Get departments response: ${response.body()}")

                    val departments = response.body()
                    departments?.let {
                        val aux = users.value?.groupBy { it.departmentID }
                        aux?.forEach { grouping ->
                            val aux2 = departments.find { grouping.key == it.id }
                            aux2?.listOfUsers = grouping.value
                        }
                        this@GetDepartmentsViewModel.departments.value = departments
                    }
                } else {
                    Log.d(TAG, "Get departments response: ${response?.errorBody()}")
                }
            } catch (e: Exception) {
                Log.d(
                    TAG,
                    "GetDepartmentsViewModel - getDepartments() failed with exception: ${e.message}"
                )
            }
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            try {
                val token: String? = App.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
                )
                val response = token?.let {
                    repository.getUsers(it)
                }

                if (response?.isSuccessful == true) {
                    Log.d(TAG, "Get Users response: ${response.body()}")
                    val usersList = response.body()
                    usersList?.let {
                        users.value = usersList
                        getDepartments()
                    }
                } else {
                    Log.d(TAG, "Get Users error response: ${response?.errorBody()}")
                }

            } catch (e: Exception) {
                Log.d(TAG, "DepartmentsViewModel - getUser() failed with exception: ${e.message}")
            }
        }
    }
}
