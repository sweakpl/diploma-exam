package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.data.remote.common.ResponseCode
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.exam_score.ExamScoreState
import com.sweak.diplomaexam.domain.repository.ExamScoreRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

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
                                Grade.fromFloat(examScoreState.roundedFinalGrade.toFloat()),
                                examScoreState.preciseFinalGrade.toFloat(),
                                Grade.fromFloat(examScoreState.presentationGrade.toFloat()),
                                Grade.fromFloat(examScoreState.diplomaGrade.toFloat()),
                                examScoreState.studyGrade.toFloat()
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