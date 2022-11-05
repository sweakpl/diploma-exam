package com.sweak.diplomaexam.data.remote

import com.sweak.diplomaexam.data.remote.dto.login.LoginRequestDto
import com.sweak.diplomaexam.data.remote.dto.login.LoginResponseDto
import com.sweak.diplomaexam.data.remote.dto.session_selection.AvailableSessionDto
import retrofit2.Response
import retrofit2.http.*

interface DiplomaExamApi {

    @POST("login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): Response<LoginResponseDto>

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