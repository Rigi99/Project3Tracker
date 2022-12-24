package com.example.project3tracker.api.model

class Task(
    var ID: Int,
    var title: String,
    var description: String,
    var createdTime: Long,
    var createdBy: Int,
    var assignedTo: Int,
    var priority: Int,
    var deadline: Long,
    var departmentID: Int,
    var status: Int
)