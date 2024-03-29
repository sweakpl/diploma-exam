package com.sweak.diplomaexam.data.remote

import com.sweak.diplomaexam.data.remote.dto.login.LoginRequestDto
import com.sweak.diplomaexam.data.remote.dto.login.LoginResponseDto
import com.sweak.diplomaexam.data.remote.dto.session.*
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
    suspend fun setSessionStatus(
        @Header("Authorization") bearerWithToken: String,
        @Body setSessionStatusRequestDto: SetSessionStatusRequestDto
    ): Response<SessionStateDto>

    @GET("examinationSession/{id}")
    suspend fun getSessionState(
        @Header("Authorization") bearerWithToken: String,
        @Path("id") sessionId: Int
    ): Response<SessionStateDto>

    @GET("examinationQuestion/getQuestions/session/{sessionId}/")
    suspend fun getQuestions(
        @Header("Authorization") bearerWithToken: String,
        @Path("sessionId") sessionId: Int
    ): Response<List<ExamQuestionDto>>

    @POST("examinationQuestion/requestRedraw/{sessionId}")
    suspend fun requestQuestionsRedraw(
        @Header("Authorization") bearerWithToken: String,
        @Path("sessionId") sessionId: Int
    ): Response<Unit>

    @POST("examinationQuestion/redrawQuestions/session/{sessionId}/")
    suspend fun redrawQuestions(
        @Header("Authorization") bearerWithToken: String,
        @Path("sessionId") sessionId: Int
    ): Response<Unit>

    @POST("/api/v1/examinationGrade/confirmReadinessToAnswerQuestions/{sessionId}")
    suspend fun confirmReadinessToAnswer(
        @Header("Authorization") bearerWithToken: String,
        @Path("sessionId") sessionId: Int
    ): Response<Unit>

    @POST("/api/v1/examinationGrade/sendQuestionsGrade")
    suspend fun submitQuestionGrades(
        @Header("Authorization") bearerWithToken: String,
        @Body submitQuestionGradesRequestDto: SubmitQuestionGradesRequestDto
    ): Response<Unit>

    @POST("/api/v1/examinationGrade/sendExamGrade")
    suspend fun submitAdditionalGrades(
        @Header("Authorization") bearerWithToken: String,
        @Body submitAdditionalGradesRequestDto: SubmitAdditionalGradesRequestDto
    ): Response<Unit>

    @GET("/api/v1/examinationGrade/session/{sessionId}")
    suspend fun getExamScore(
        @Header("Authorization") bearerWithToken: String,
        @Path("sessionId") sessionId: Int
    ): Response<ExamScoreDto>
}