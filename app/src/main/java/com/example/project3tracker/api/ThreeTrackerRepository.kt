package com.example.project3tracker.api

import com.example.project3tracker.api.model.*
import retrofit2.Response

class ThreeTrackerRepository {
    suspend fun login(loginRequestBody: LoginRequestBody): Response<LoginResponse> {
        return RetrofitInstance.USER_API_SERVICE.login(loginRequestBody)
    }

    suspend fun getTasks(token: String): Response<List<TaskResponse>> {
        return RetrofitInstance.USER_API_SERVICE.getTasks(token)
    }

    suspend fun createTask(createTaskRequestBody: CreateTaskRequestBody): Response<CreateTaskResponse> {
        return RetrofitInstance.USER_API_SERVICE.createTask(createTaskRequestBody)
    }

    suspend fun updateUser(updateUserRequestBody: UpdateProfileRequest): Response<UpdateProfileResponse> {
        return RetrofitInstance.USER_API_SERVICE.updateUser(updateUserRequestBody)
    }
}