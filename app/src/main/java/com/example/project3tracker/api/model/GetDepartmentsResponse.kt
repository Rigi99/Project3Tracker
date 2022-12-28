package com.example.project3tracker.api.model

import com.google.gson.annotations.SerializedName

data class GetDepartmentsResponse(
    @SerializedName("ID")
    var id: Int,

    @SerializedName("name")
    var name: String,
) {
    var listOfUsers: List<GetProfileResponse>? = null
}