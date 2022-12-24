package com.example.project3tracker.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project3tracker.R
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.viewmodel.NewTaskViewModel
import com.example.project3tracker.viewmodel.NewTaskViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class NewTaskFragment : Fragment(R.layout.fragment_newtask) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var newTaskViewModel: NewTaskViewModel
    private lateinit var titleEditText: EditText
    private lateinit var departmentIDEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var statusSpinner: Spinner
    private lateinit var prioritySpinner: Spinner
    private lateinit var deadlineDatePicker: DatePicker
    private lateinit var assignedToUserIDEditText: EditText
    private var numbers = arrayOf(0, 1, 2, 3)
    private lateinit var adapter: ArrayAdapter<Int>

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newTaskViewModelFactory = NewTaskViewModelFactory(ThreeTrackerRepository())
        newTaskViewModel = ViewModelProvider(
            requireActivity(),
            newTaskViewModelFactory
        )[NewTaskViewModel::class.java]
        val view = inflater.inflate(R.layout.fragment_newtask, container, false)
        titleEditText = view.findViewById(R.id.editTextTextTaskTitle)
        departmentIDEditText = view.findViewById(R.id.editTextDepartmentID)
        descriptionEditText = view.findViewById(R.id.editTextTextTaskDescription)
        statusSpinner = view.findViewById(R.id.spinnerStatus)
        prioritySpinner = view.findViewById(R.id.spinnerPriority)
        deadlineDatePicker = view.findViewById(R.id.datePickerDeadline)
        assignedToUserIDEditText = view.findViewById(R.id.editTextUserID)
        adapter = ArrayAdapter<Int>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            numbers
        )
        statusSpinner.adapter = adapter
        prioritySpinner.adapter = adapter
        val today = Calendar.getInstance()
        var deadline = today.timeInMillis
        deadlineDatePicker.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            today.set(year, month, day + 1, 0, 0)
            deadline = today.timeInMillis
        }

        val addTaskButton: Button = view.findViewById(R.id.addTaskButton)
        addTaskButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val department = departmentIDEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val status = statusSpinner.selectedItem.toString()
            val priority = prioritySpinner.selectedItem.toString()
            val assignedTo = assignedToUserIDEditText.text.toString()

            newTaskViewModel.createTask(
                title,
                description,
                assignedTo.toInt(),
                priority.toInt(),
                deadline,
                department.toInt(),
                status.toInt()
            )
        }
        newTaskViewModel.isSuccessful.observe(this.viewLifecycleOwner) {
            Log.d(TAG, "Task created successfully = $it")
            if (it) {
                clearFields()
            } else {
                Toast.makeText(activity, "Task creation failed!", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun clearFields() {
        titleEditText.setText("")
        departmentIDEditText.setText("")
        descriptionEditText.setText("")
        statusSpinner.adapter = adapter
        prioritySpinner.adapter = adapter
        val today = Calendar.getInstance()
        deadlineDatePicker.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            today.set(year, month, day, 0, 0)
        }
        assignedToUserIDEditText.setText("")
    }
}