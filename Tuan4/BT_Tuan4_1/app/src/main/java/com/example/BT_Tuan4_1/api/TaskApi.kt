package com.example.BT_Tuan4_1.api

import com.example.BT_Tuan4_1.model.TaskResponse
import retrofit2.Response
import retrofit2.http.GET

interface TaskApi {
    @GET("researchUTH/tasks")
    suspend fun getTasks(): Response<TaskResponse>
}