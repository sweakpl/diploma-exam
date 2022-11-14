package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.data.common.ResponseCode
import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.remote.API_ROLE_EXAMINER
import com.sweak.diplomaexam.data.remote.API_ROLE_STUDENT
import com.sweak.diplomaexam.data.remote.API_SESSION_STATUS_ANSWERING_QUESTIONS
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.data.remote.dto.session.SetSessionStateRequestDto
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.domain.model.common.User
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.domain.model.questions_draw.QuestionsDrawState
import com.sweak.diplomaexam.domain.repository.QuestionsDrawRepository
import retrofit2.HttpException
import java.io.IOException

class QuestionsDrawRepositoryImpl(
    private val diplomaExamApi: DiplomaExamApi,
    private val userSessionManager: UserSessionManager
) : QuestionsDrawRepository {

    override suspend fun getQuestionsDrawState(): Resource<QuestionsDrawState> {
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

                        val areQuestionsConfirmed =
                            sessionState.status == API_SESSION_STATUS_ANSWERING_QUESTIONS

                        if (areQuestionsConfirmed) {
                            return Resource.Success(
                                QuestionsDrawState(areQuestionsConfirmed = true)
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

                        val examQuestions: List<ExamQuestion>?
                        val waitingForDecisionFrom: UserRole

                        if (!sessionState.haveQuestionsBeenDrawn) {
                            examQuestions = null
                            waitingForDecisionFrom = UserRole.USER_STUDENT
                        } else {
                            examQuestions = when (val examQuestionsResource = getExamQuestions()) {
                                is Resource.Failure ->
                                    return Resource.Failure(error = examQuestionsResource.error!!)
                                is Resource.Success ->
                                    examQuestionsResource.data
                                else -> null
                            }

                            waitingForDecisionFrom = if (sessionState.haveStudentRequestedRedraw) {
                                if (sessionState.haveQuestionsBeenRedrawn) {
                                    UserRole.USER_STUDENT
                                } else {
                                    UserRole.USER_EXAMINER
                                }
                            } else {
                                UserRole.USER_STUDENT
                            }
                        }

                        Resource.Success(
                            QuestionsDrawState(
                                currentUser = currentUser,
                                otherUser = otherUser,
                                questions = examQuestions,
                                hasStudentRequestedRedraw = sessionState.haveStudentRequestedRedraw,
                                waitingForDecisionFrom = waitingForDecisionFrom,
                                areQuestionsConfirmed = false
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

    override suspend fun getExamQuestions(): Resource<List<ExamQuestion>> {
        try {
            val response = diplomaExamApi.getExamQuestions(
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

    override suspend fun requestQuestionsRedraw(): Resource<Unit> {
        try {
            val response = diplomaExamApi.requestQuestionsRedraw(
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

    override suspend fun redrawQuestions(): Resource<Unit> {
        try {
            val response = diplomaExamApi.redrawExamQuestions(
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

    override suspend fun acceptDrawnQuestions(): Resource<Unit> {
        try {
            val response = diplomaExamApi.setSessionState(
                "Bearer ${userSessionManager.getSessionToken()}",
                SetSessionStateRequestDto(
                    userSessionManager.getSessionId(),
                    API_SESSION_STATUS_ANSWERING_QUESTIONS
                )
            )

            return when (response.code()) {
                ResponseCode.OK.codeInt -> {
                    if (response.body() == null) {
                        Resource.Failure(Error.UnknownError)
                    } else {
                        Resource.Success(Unit)
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
}