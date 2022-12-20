package com.example.project3tracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project3tracker.R
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.viewmodel.SettingsViewModel
import com.example.project3tracker.viewmodel.SettingsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var profileSettings: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = SettingsViewModelFactory(ThreeTrackerRepository())
        profileSettings = ViewModelProvider(this, factory)[SettingsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationView.visibility = View.VISIBLE
    }

}