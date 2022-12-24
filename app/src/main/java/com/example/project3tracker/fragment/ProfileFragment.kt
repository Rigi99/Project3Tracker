package com.example.project3tracker.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project3tracker.R
import com.example.project3tracker.api.ThreeTrackerRepository
import com.example.project3tracker.viewmodel.GetProfileViewModel
import com.example.project3tracker.viewmodel.GetProfileViewModelFactory
import com.example.project3tracker.viewmodel.SettingsViewModel
import com.example.project3tracker.viewmodel.SettingsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.Executors


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        private val TAG: String = javaClass.simpleName
    }

    private lateinit var profileSettings: SettingsViewModel
    private lateinit var profileData: GetProfileViewModel
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var phone: EditText
    private lateinit var imageURL: EditText
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val settingsViewModelFactory = SettingsViewModelFactory(ThreeTrackerRepository())
        val profileViewModelFactory = GetProfileViewModelFactory(ThreeTrackerRepository())
        profileSettings =
            ViewModelProvider(
                requireActivity(),
                settingsViewModelFactory
            )[SettingsViewModel::class.java]
        profileData =
            ViewModelProvider(
                requireActivity(),
                profileViewModelFactory
            )[GetProfileViewModel::class.java]
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        firstNameEditText = view.findViewById(R.id.editTextTextFirstName)
        lastNameEditText = view.findViewById(R.id.editTextTextLastName)
        locationEditText = view.findViewById(R.id.editTextTextLocation)
        phone = view.findViewById(R.id.editTextPhone)
        imageURL = view.findViewById(R.id.editTextTextImageURL)
        imageView = view.findViewById(R.id.imageView3)
        profileData.data.observe(viewLifecycleOwner) {
            firstNameEditText.setText(it.firstName)
            lastNameEditText.setText(it.lastName)
            locationEditText.setText(it.location)
            phone.setText(it.phoneNumber)
            imageURL.setText(it.image)
            val executor = Executors.newSingleThreadExecutor()
            val handler = Handler(Looper.getMainLooper())
            var image: Bitmap?
            executor.execute {
                try {
                    Log.e("XXX", imageURL.text.toString())
                    val `in` = java.net.URL(imageURL.text.toString()).openStream()
                    image = BitmapFactory.decodeStream(`in`)
                    handler.post {
                        imageView.setImageBitmap(image)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        val updateButton: Button = view.findViewById(R.id.updateButton)

        updateButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val location = locationEditText.text.toString()
            val phoneNumber = phone.text.toString()
            val imageUrl = imageURL.text.toString()

            profileSettings.updateUser(firstName, lastName, location, phoneNumber, imageUrl)

            profileSettings.isSuccessful.observe(this.viewLifecycleOwner) {
                Log.d(TAG, "Update done successfully = $it")
                if (it) {
                    profileData.getUserData()
                } else {
                    Toast.makeText(activity, "Update failed!", Toast.LENGTH_LONG).show()
                }
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

}