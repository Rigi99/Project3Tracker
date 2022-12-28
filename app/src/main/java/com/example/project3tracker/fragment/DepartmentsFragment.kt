package com.example.project3tracker.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project3tracker.R
import com.example.project3tracker.adapter.DepartmentsListAdapter
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.api.model.GetDepartmentsResponse
import com.example.project3tracker.viewmodel.GetDepartmentsViewModel
import com.example.project3tracker.viewmodel.GetDepartmentsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class DepartmentsFragment : Fragment(R.layout.fragment_task_list) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var getDepartmentsViewModel: GetDepartmentsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DepartmentsListAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_departments, container, false)
        val factory = GetDepartmentsViewModelFactory(ThreeTrackerRepository())
        getDepartmentsViewModel = ViewModelProvider(requireActivity(), factory)[GetDepartmentsViewModel::class.java]
        recyclerView = view.findViewById(R.id.recycler_view)
        setupRecyclerView()
        getDepartmentsViewModel.departments.observe(viewLifecycleOwner) {
            Log.d(TAG, "Departments list = $it")
            adapter.setData(getDepartmentsViewModel.departments.value as ArrayList<GetDepartmentsResponse>)
            adapter.notifyDataSetChanged()
        }
        return view
    }

    private fun setupRecyclerView() {
        adapter = DepartmentsListAdapter(ArrayList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.setHasFixedSize(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)

        bottomNavigationView.visibility = View.VISIBLE
    }
}