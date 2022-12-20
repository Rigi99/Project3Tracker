package com.example.project3tracker.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.project3tracker.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewTaskFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val view2 = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)

        view2.visibility = View.VISIBLE
    }
}