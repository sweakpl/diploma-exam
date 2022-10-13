package com.sweak.diplomaexam.data.remote

import com.sweak.diplomaexam.domain.model.login.LoginRequest
import com.sweak.diplomaexam.domain.model.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DiplomaExamApi {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}