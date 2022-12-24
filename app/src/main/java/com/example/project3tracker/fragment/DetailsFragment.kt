package com.example.project3tracker.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.project3tracker.R
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.viewmodel.TaskViewModel
import com.example.project3tracker.viewmodel.TaskViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment(R.layout.fragment_detail) {
//    companion object {
//        @Suppress("JAVA_CLASS_ON_COMPANION")
//        private val TAG: String = javaClass.simpleName
//    }

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var createdOnTextView: TextView
    private lateinit var createdByTextView: TextView
    private lateinit var departmentIDTextView: TextView
    private lateinit var assignedToTextView: TextView
    private lateinit var taskPriorityTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var deadlineTextView: TextView
    private lateinit var okButton: Button

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val taskViewModelFactory = TaskViewModelFactory(ThreeTrackerRepository())
        taskViewModel =
            ViewModelProvider(
                requireActivity(),
                taskViewModelFactory
            )[TaskViewModel::class.java]
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        titleTextView = view.findViewById(R.id.textViewTitle)
        descriptionTextView = view.findViewById(R.id.textViewDescription)
        createdOnTextView = view.findViewById(R.id.textViewCreatedOn)
        createdByTextView = view.findViewById(R.id.textViewCreatedBy)
        departmentIDTextView = view.findViewById(R.id.textViewDepartmentID)
        assignedToTextView = view.findViewById(R.id.textViewAssignedTo)
        taskPriorityTextView = view.findViewById(R.id.textViewPriority)
        statusTextView = view.findViewById(R.id.textViewStatus)
        deadlineTextView = view.findViewById(R.id.textViewDeadline)

        val format = SimpleDateFormat("yyyy.MM.dd")
        titleTextView.text = taskViewModel.selectedTask.title
        descriptionTextView.append("\n${taskViewModel.selectedTask.description}")
        createdOnTextView.append(" ${format.format(Date(taskViewModel.selectedTask.createdTime))}")
        createdByTextView.append(" ${taskViewModel.selectedTask.createdBy}")
        departmentIDTextView.append(" ${taskViewModel.selectedTask.departmentID}")
        assignedToTextView.append(" ${taskViewModel.selectedTask.assignedTo}")
        when (taskViewModel.selectedTask.priority) {
            0 -> {
                taskPriorityTextView.setBackgroundColor(Color.RED)
            }
            1 -> {
                taskPriorityTextView.setBackgroundColor(Color.YELLOW)
            }
            2 -> {
                taskPriorityTextView.setBackgroundColor(Color.GREEN)
            }
        }
        statusTextView.append(" ${taskViewModel.selectedTask.status}")
        deadlineTextView.append("\n${format.format(Date(taskViewModel.selectedTask.deadline))}")
        okButton = view.findViewById(R.id.okButton)

        okButton.setOnClickListener {
            findNavController().navigate(R.id.taskListFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationView.visibility = View.VISIBLE
    }

}