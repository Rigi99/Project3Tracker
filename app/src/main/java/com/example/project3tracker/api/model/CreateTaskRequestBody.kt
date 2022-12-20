package com.example.project3tracker.api.model

import com.google.gson.annotations.SerializedName
import java.sql.Time

data class CreateTaskRequestBody(
    @SerializedName("description")
    var descrition: String,
    @SerializedName("assignedToUserId")
    var assignedToUserId: Int,
    @SerializedName("priority")
    var priority: Int,
    @SerializedName("deadline")
    var deadline: Time,
    @SerializedName("departmentId")
    var departmentId: Int,
    @SerializedName("status")
    var status: Int
)