package com.sweak.diplomaexam.data.remote

import com.sweak.diplomaexam.data.remote.dto.AvailableSessionDto
import com.sweak.diplomaexam.domain.model.login.LoginRequest
import com.sweak.diplomaexam.domain.model.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DiplomaExamApi {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("examinationSession/getAvailableSessionStudents")
    suspend fun getAvailableSessions(
        @Header("Authorization") bearerWithToken: String
    ): Response<List<AvailableSessionDto>>

    @POST("examinationSession/selectExaminerSession/{sessionId}")
    suspend fun selectSession(
        @Header("Authorization") bearerWithToken: String,
        @Path("sessionId") sessionId: Int
    ): Response<AvailableSessionDto>
}