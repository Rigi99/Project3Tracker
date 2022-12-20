package com.example.project3tracker.api.model

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("message")
    var message: String
) {
    override fun toString(): String {
        return "UpdateUserResponse(" +
                "userId='$message"
    }
}
