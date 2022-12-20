package com.example.project3tracker.api.model

import com.google.gson.annotations.SerializedName

data class CreateTaskResponse(
    @SerializedName("message")
    var message: String
) {
    override fun toString(): String {
        return "LoginResponse(" +
                "userId='$message"
    }
}