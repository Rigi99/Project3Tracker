package com.example.project3tracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project3tracker.api.ThreeTrackerRepository

class GetDepartmentsViewModelFactory(private val repository: ThreeTrackerRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return GetDepartmentsViewModel(repository) as T
    }
}