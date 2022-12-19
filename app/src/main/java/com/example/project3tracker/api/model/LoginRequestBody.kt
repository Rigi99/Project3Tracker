package com.example.project3tracker.api.model

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(
    @SerializedName("email")
    var email: String,
    @SerializedName("passwordKey")
    var password: String
)