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

    suspend fun createTask(
        token: String,
        createTaskRequestBody: CreateTaskRequestBody
    ): Response<CreateTaskResponse> {
        return RetrofitInstance.USER_API_SERVICE.createTask(token, createTaskRequestBody)
    }

    suspend fun updateUser(
        token: String,
        updateUserRequestBody: UpdateProfileRequest
    ): Response<UpdateProfileResponse> {
        return RetrofitInstance.USER_API_SERVICE.updateUser(token, updateUserRequestBody)
    }

    suspend fun getUserData(token: String): Response<GetProfileResponse> {
        return RetrofitInstance.USER_API_SERVICE.getUserData(token)
    }

    suspend fun getDepartments(token: String): Response<List<GetDepartmentsResponse>> {
        return RetrofitInstance.USER_API_SERVICE.getDepartments(token)
    }

    suspend fun getUsers(token: String): Response<List<GetProfileResponse>> {
        return RetrofitInstance.USER_API_SERVICE.getUsers(token)
    }
}