package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.data.common.ResponseCode
import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.remote.*
import com.sweak.diplomaexam.data.remote.dto.session.SetSessionStateRequestDto
import com.sweak.diplomaexam.data.remote.dto.session.SubmitAdditionalGradesRequestDto
import com.sweak.diplomaexam.data.remote.dto.session.SubmitQuestionGradesRequestDto
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.*
import com.sweak.diplomaexam.domain.model.questions_answering.QuestionsAnsweringState
import com.sweak.diplomaexam.domain.repository.QuestionsAnsweringRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class QuestionsAnsweringRepositoryImpl @Inject constructor(
    private val diplomaExamApi: DiplomaExamApi,
    private val userSessionManager: UserSessionManager
) : QuestionsAnsweringRepository {

    override suspend fun getQuestionsAnsweringState(): Resource<QuestionsAnsweringState> {
        try {
            val response = diplomaExamApi.getSessionState(
                "Bearer ${userSessionManager.getSessionToken()}",
                userSessionManager.getSessionId()
            )

            return when (response.code()) {
                ResponseCode.OK.codeInt -> {
                    if (response.body() == null) {
                        Resource.Failure(Error.UnknownError)
                    } else {
                        val sessionState = response.body()!!

                        val areAllGradesReady =
                            sessionState.status == API_SESSION_STATUS_SUMMARY

                        if (areAllGradesReady) {
                            return Resource.Success(
                                QuestionsAnsweringState(isGradingCompleted = true)
                            )
                        }

                        val currentUser: User = try {
                            User(
                                UserRole.valueOf(userSessionManager.getUserRole() ?: ""),
                                userSessionManager.getUserEmail()
                            )
                        } catch (illegalArgumentException: IllegalArgumentException) {
                            return Resource.Failure(Error.UnauthorizedError(null))
                        }

                        val otherUser: User? = sessionState.userDtos.find {
                            it.role ==
                                    if (currentUser.role == UserRole.USER_EXAMINER) API_ROLE_STUDENT
                                    else API_ROLE_EXAMINER
                        }?.run {
                            User(
                                role =
                                if (this.role == API_ROLE_STUDENT) UserRole.USER_STUDENT
                                else UserRole.USER_EXAMINER,
                                email = this.email
                            )
                        }

                        val examQuestions: List<ExamQuestion> =
                            when (val examQuestionsResource = getQuestions()) {
                                is Resource.Failure ->
                                    return Resource.Failure(error = examQuestionsResource.error!!)
                                is Resource.Success -> examQuestionsResource.data!!
                                else -> emptyList()
                            }

                        Resource.Success(
                            QuestionsAnsweringState(
                                currentUser = currentUser,
                                otherUser = otherUser,
                                questions = examQuestions,
                                isWaitingForStudentReadiness = !sessionState.areQuestionsBeingGraded,
                                isWaitingForFinalEvaluation = sessionState.isExamBeingSummarized,
                                isGradingCompleted = false
                            )
                        )
                    }
                }
                ResponseCode.UNAUTHORIZED.codeInt ->
                    Resource.Failure(Error.UnauthorizedError(response.message()))
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

    override suspend fun getQuestions(): Resource<List<ExamQuestion>> {
        try {
            val response = diplomaExamApi.getQuestions(
                "Bearer ${userSessionManager.getSessionToken()}",
                userSessionManager.getSessionId()
            )

            return when (response.code()) {
                ResponseCode.OK.codeInt -> {
                    if (response.body() == null) {
                        Resource.Failure(Error.UnknownError)
                    } else {
                        Resource.Success(
                            response.body()!!.sortedBy {
                                it.id
                            }.mapIndexed { index, examQuestionDto ->
                                ExamQuestion(
                                    id = examQuestionDto.id,
                                    number = index + 1,
                                    question = examQuestionDto.question,
                                    answer = examQuestionDto.answer
                                )
                            }
                        )
                    }
                }
                ResponseCode.UNAUTHORIZED.codeInt ->
                    Resource.Failure(Error.UnauthorizedError(response.message()))
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

    override suspend fun confirmReadinessToAnswer(): Resource<Unit> {
        try {
            val response = diplomaExamApi.confirmReadinessToAnswer(
                "Bearer ${userSessionManager.getSessionToken()}",
                userSessionManager.getSessionId()
            )

            return when (response.code()) {
                ResponseCode.OK.codeInt -> Resource.Success(Unit)
                ResponseCode.UNAUTHORIZED.codeInt ->
                    Resource.Failure(Error.UnauthorizedError(response.message()))
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

    override suspend fun submitQuestionGrades(
        gradesList: List<Grade>
    ): Resource<Unit> {
        try {
            val response = diplomaExamApi.submitQuestionGrades(
                "Bearer ${userSessionManager.getSessionToken()}",
                SubmitQuestionGradesRequestDto(
                    sessionId = userSessionManager.getSessionId(),
                    firstQuestionGrade = gradesList[0].ordinal,
                    secondQuestionGrade = gradesList[1].ordinal,
                    thirdQuestionGrade = gradesList[2].ordinal
                )
            )

            return when (response.code()) {
                ResponseCode.OK.codeInt -> Resource.Success(Unit)
                ResponseCode.UNAUTHORIZED.codeInt ->
                    Resource.Failure(Error.UnauthorizedError(response.message()))
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

    override suspend fun submitAdditionalGrades(
        thesisGrade: Grade,
        thesisPresentationGrade: Grade,
        courseOfStudiesGrade: Grade
    ): Resource<Unit> {
        try {
            val response = diplomaExamApi.submitAdditionalGrades(
                "Bearer ${userSessionManager.getSessionToken()}",
                SubmitAdditionalGradesRequestDto(
                    sessionId = userSessionManager.getSessionId(),
                    diplomaGrade = thesisGrade.ordinal,
                    presentationGrade = thesisPresentationGrade.ordinal,
                    studyGrade = courseOfStudiesGrade.ordinal
                )
            )

            when (response.code()) {
                ResponseCode.OK.codeInt -> {
                    val setSessionToSummaryResponse = diplomaExamApi.setSessionState(
                        "Bearer ${userSessionManager.getSessionToken()}",
                        SetSessionStateRequestDto(
                            userSessionManager.getSessionId(),
                            API_SESSION_STATUS_SUMMARY
                        )
                    )

                    return when (setSessionToSummaryResponse.code()) {
                        ResponseCode.OK.codeInt -> {
                            if (setSessionToSummaryResponse.body() == null) {
                                Resource.Failure(Error.UnknownError)
                            } else {
                                Resource.Success(Unit)
                            }
                        }
                        ResponseCode.UNAUTHORIZED.codeInt ->
                            Resource.Failure(
                                Error.UnauthorizedError(setSessionToSummaryResponse.message())
                            )
                        else -> Resource.Failure(Error.UnknownError)
                    }
                }
                ResponseCode.UNAUTHORIZED.codeInt ->
                    return Resource.Failure(Error.UnauthorizedError(response.message()))
                else -> return Resource.Failure(Error.UnknownError)
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
}