package com.example.project3tracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project3tracker.api.ThreeTrackerRepository

class GetProfileViewModelFactory (private val repository: ThreeTrackerRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GetProfileViewModel(repository) as T
    }
}