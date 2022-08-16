package com.example.diplomaexam.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface DiplomaExamApi {

    @GET("hello")
    suspend fun getHello(): Response<String>
}