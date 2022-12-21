package com.example.project3tracker.api

import com.example.project3tracker.api.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApiService {

    @POST(BackendConstants.LOGIN_URL)
    suspend fun login(@Body loginRequest: LoginRequestBody): Response<LoginResponse>

    @GET(BackendConstants.GET_TASKS_URL)
    suspend fun getTasks(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<List<TaskResponse>>

    @POST(BackendConstants.CREATE_TASK_URL)
    suspend fun createTask(
        @Header(BackendConstants.HEADER_TOKEN) token: String,
        @Body createTaskRequest: CreateTaskRequestBody
    ): Response<CreateTaskResponse>

    @POST(BackendConstants.UPDATE_USER)
    suspend fun updateUser(
        @Header(BackendConstants.HEADER_TOKEN) token: String,
        @Body updateUserRequest: UpdateProfileRequest
    ): Response<UpdateProfileResponse>

    @GET(BackendConstants.GET_USER)
    suspend fun getUserData(@Header(BackendConstants.HEADER_TOKEN) token: String): Response<GetProfileResponse>

}