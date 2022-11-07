package com.sweak.diplomaexam.data.remote

import com.sweak.diplomaexam.data.remote.dto.login.LoginRequestDto
import com.sweak.diplomaexam.data.remote.dto.login.LoginResponseDto
import com.sweak.diplomaexam.data.remote.dto.session.SessionStateDto
import com.sweak.diplomaexam.data.remote.dto.session.SetSessionStateRequestDto
import retrofit2.Response
import retrofit2.http.*

interface DiplomaExamApi {

    @POST("login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): Response<LoginResponseDto>

    @GET("examinationSession/getAvailableSessionStudents")
    suspend fun getAvailableSessions(
        @Header("Authorization") bearerWithToken: String
    ): Response<List<SessionStateDto>>

    @POST("examinationSession/setSessionState")
    suspend fun setSessionState(
        @Header("Authorization") bearerWithToken: String,
        @Body setSessionStateRequestDto: SetSessionStateRequestDto
    ): Response<SessionStateDto>
}