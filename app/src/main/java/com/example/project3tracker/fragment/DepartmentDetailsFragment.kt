package com.example.project3tracker.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project3tracker.R
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.viewmodel.GetDepartmentsViewModel
import com.example.project3tracker.viewmodel.GetDepartmentsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class DepartmentDetailsFragment : Fragment(R.layout.fragment_department_details) {
    private lateinit var getDepartmentsViewModel: GetDepartmentsViewModel
    private lateinit var departmentNameTextView: TextView
    private lateinit var membersTextView: TextView

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val departmentViewModelFactory = GetDepartmentsViewModelFactory(ThreeTrackerRepository())
        getDepartmentsViewModel =
            ViewModelProvider(
                requireActivity(),
                departmentViewModelFactory
            )[GetDepartmentsViewModel::class.java]
        val view = inflater.inflate(R.layout.fragment_department_details, container, false)
        departmentNameTextView = view.findViewById(R.id.textViewDepartmentMembers)
        membersTextView = view.findViewById(R.id.textViewMembers)

        departmentNameTextView.text = getDepartmentsViewModel.selectedDepartment.name
        val users = getDepartmentsViewModel.selectedDepartment.members
        val strBuilder = StringBuilder()
        users.forEach {
            val a = it.firstName
            val b = it.lastName
            val aux = a.plus(" ").plus(b)
            strBuilder.appendLine(aux)
        }
        membersTextView.text = strBuilder
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationView.visibility = View.VISIBLE
    }
}