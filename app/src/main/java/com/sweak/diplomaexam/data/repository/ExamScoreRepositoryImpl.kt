package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.data.remote.common.ResponseCode
import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.domain.model.exam_score.ExamScoreState
import com.sweak.diplomaexam.domain.repository.ExamScoreRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.math.floor

class ExamScoreRepositoryImpl @Inject constructor(
    private val diplomaExamApi: DiplomaExamApi,
    private val userSessionManager: UserSessionManager
) : ExamScoreRepository {

    override suspend fun getExamScoreState(): Resource<ExamScoreState> {
        try {
            val response = diplomaExamApi.getExamScore(
                "Bearer ${userSessionManager.getSessionToken()}",
                userSessionManager.getSessionId()
            )

            return when (response.code()) {
                ResponseCode.OK.codeInt -> {
                    if (response.body() == null) {
                        Resource.Failure(Error.UnknownError)
                    } else {
                        val examScoreState = response.body()!!

                        Resource.Success(
                            ExamScoreState(
                                Grade.fromFloat(
                                    // Temporarily rounding the grade here instead of on the server
                                    examScoreState.finalGrade.toFloat().run {
                                        0.5 * floor(this / 0.5)
                                    }.toFloat()
                                ),
                                Grade.fromFloat(examScoreState.presentationGrade.toFloat()),
                                Grade.fromFloat(examScoreState.diplomaGrade.toFloat()),
                                Grade.fromFloat(
                                    // Temporarily rounding the grade here instead of on the server
                                    examScoreState.finalGrade.toFloat().run {
                                        0.5 * floor(this / 0.5)
                                    }.toFloat()
                                )
                            )
                        )
                    }
                }
                ResponseCode.UNAUTHORIZED.codeInt ->
                    Resource.Failure(Error.UnauthorizedError(response.message()))
                ResponseCode.INTERNAL_SERVER_ERROR.codeInt ->
                    Resource.Failure(Error.InternalServerError(response.message()))
                else -> Resource.Failure(Error.UnknownError)
            }
        } catch (httpException: HttpException) {
            return Resource.Failure(
                Error.HttpError(
                    httpException.code(),
                    httpException.localizedMessage ?: httpException.message
                )
            )
        } catch (ioException: IOException) {
            return Resource.Failure(Error.IOError(ioException.message))
        }
    }

    override fun finishExam() {
        userSessionManager.cleanUpSession()
    }
}