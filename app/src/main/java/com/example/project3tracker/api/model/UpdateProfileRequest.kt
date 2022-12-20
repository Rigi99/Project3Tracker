package com.example.project3tracker.api.model

import com.google.gson.annotations.SerializedName
import java.sql.Time

data class UpdateProfileRequest(
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("location")
    var location: String,
    @SerializedName("phoneNumber")
    var phoneNumber: String,
    @SerializedName("imageUrl")
    var imageUrl: String
)
