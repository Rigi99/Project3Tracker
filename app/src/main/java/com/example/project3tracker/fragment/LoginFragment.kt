package com.example.project3tracker.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.project3tracker.App
import com.example.project3tracker.R
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.manager.SharedPreferencesManager
import com.example.project3tracker.viewmodel.LoginViewModel
import com.example.project3tracker.viewmodel.LoginViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LoginFragment : Fragment() {

    companion object {
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(ThreeTrackerRepository())
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val userNameEditText: EditText = view.findViewById(R.id.edittext_name_login_fragment)
        val passwordEditText: EditText = view.findViewById(R.id.edittext_password_login_fragment)
        val logInButton: Button = view.findViewById(R.id.log_in_button)

        Log.d(
            TAG,
            "token = " + App.sharedPreferences.getStringValue(
                SharedPreferencesManager.KEY_TOKEN,
                "Empty token!"
            )
        )

        logInButton.setOnClickListener {
            val username = userNameEditText.text.toString()
            val password = passwordEditText.text.toString()

            loginViewModel.login(username, password)

            loginViewModel.isSuccessful.observe(this.viewLifecycleOwner) {
                Log.d(TAG, "Logged in successfully = $it")
                if (it) {
                    findNavController().navigate(R.id.taskListFragment)
                } else {
                    Toast.makeText(activity, "Wrong email or password!", Toast.LENGTH_LONG).show()
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val view2 = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)

        view2.visibility = View.GONE
    }
}