package com.example.project3tracker.api.model

import com.google.gson.annotations.SerializedName

data class CreateTaskRequestBody(
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("assignedToUserId")
    var assignedToUserId: Int,
    @SerializedName("priority")
    var priority: Int,
    @SerializedName("deadline")
    var deadline: Long,
    @SerializedName("departmentId")
    var departmentId: Int,
    @SerializedName("status")
    var status: Int
)