package com.example.project3tracker.api

import com.example.project3tracker.api.model.LoginRequestBody
import com.example.project3tracker.api.model.LoginResponse
import com.example.project3tracker.api.model.TaskResponse
import retrofit2.Response

class ThreeTrackerRepository {
    suspend fun login(loginRequestBody: LoginRequestBody): Response<LoginResponse> {
        return RetrofitInstance.USER_API_SERVICE.login(loginRequestBody)
    }

    suspend fun getTasks(token: String): Response<List<TaskResponse>> {
        return RetrofitInstance.USER_API_SERVICE.getTasks(token)
    }
}