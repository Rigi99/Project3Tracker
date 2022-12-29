package com.example.project3tracker.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project3tracker.R
import com.example.project3tracker.adapter.TasksListAdapter
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.api.model.GetProfileResponse
import com.example.project3tracker.api.model.Task
import com.example.project3tracker.api.model.TaskResponse
import com.example.project3tracker.viewmodel.TaskViewModel
import com.example.project3tracker.viewmodel.TaskViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class TaskListFragment : Fragment(R.layout.fragment_task_list),
    TasksListAdapter.OnItemClickListener,
    TasksListAdapter.OnItemLongClickListener {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var tasksViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TasksListAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        val factory = TaskViewModelFactory(ThreeTrackerRepository())
        tasksViewModel = ViewModelProvider(requireActivity(), factory)[TaskViewModel::class.java]
        recyclerView = view.findViewById(R.id.recycler_view)
        setupRecyclerView()
        tasksViewModel.tasks.observe(viewLifecycleOwner) {
            Log.d(TAG, "Tasks list = $it")
            adapter.setData(tasksViewModel.tasks.value as ArrayList<TaskResponse>)
            adapter.notifyDataSetChanged()
        }
        tasksViewModel.userData.observe(viewLifecycleOwner) {
            adapter.setUser(tasksViewModel.userData.value as GetProfileResponse)
        }
        return view
    }

    private fun setupRecyclerView() {
        adapter = TasksListAdapter(ArrayList(), requireContext(), this, this)
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

    override fun onItemClick(position: Int) {
        val current = tasksViewModel.tasks.value?.get(position)
        if (current != null) {
            tasksViewModel.selectedTask = Task(
                current.id,
                current.title,
                current.description,
                current.createdTime,
                current.createdByUserID,
                current.assignedToUserID,
                current.priority,
                current.deadline,
                current.departmentID,
                current.status
            )
            findNavController().navigate(R.id.detailsFragment)
        }
    }

    override fun onItemLongClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)

        bottomNavigationView.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        tasksViewModel.getData()
    }
}