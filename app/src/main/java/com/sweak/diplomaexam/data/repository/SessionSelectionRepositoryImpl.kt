package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.data.remote.common.ResponseCode
import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.remote.common.API_ROLE_STUDENT
import com.sweak.diplomaexam.data.remote.common.API_SESSION_STATUS_INACTIVE
import com.sweak.diplomaexam.data.remote.common.API_SESSION_STATUS_LOBBY
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.data.remote.dto.session.SetSessionStatusRequestDto
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession
import com.sweak.diplomaexam.domain.repository.SessionSelectionRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SessionSelectionRepositoryImpl @Inject constructor(
    private val diplomaExamApi: DiplomaExamApi,
    private val userSessionManager: UserSessionManager
) : SessionSelectionRepository {

    override suspend fun getAvailableSessions(): Resource<List<AvailableSession>> {
        try {
            val response = diplomaExamApi.getAvailableSessions(
                "Bearer ${userSessionManager.getSessionToken()}"
            )

            return when (response.code()) {
                ResponseCode.OK.codeInt -> {
                    if (response.body() == null) {
                        Resource.Failure(Error.UnknownError)
                    } else {
                        val availableSessions = response.body()!!

                        Resource.Success(
                            availableSessions.map { sessionStateDto ->
                                AvailableSession(
                                    sessionStateDto.id,
                                    sessionStateDto.userDtos.find { userDto ->
                                        userDto.role == API_ROLE_STUDENT
                                    }?.email ?: ""
                                )
                            }.filter {
                                it.studentEmail.isNotEmpty()
                            }
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

    override suspend fun selectSession(selectedSession: AvailableSession): Resource<Unit> {
        try {
            val sessionStatus = getSessionStatus(selectedSession.sessionId)

            if (sessionStatus is Resource.Failure) {
                return Resource.Failure(sessionStatus.error!!)
            }

            if (sessionStatus.data != null &&
                sessionStatus.data != API_SESSION_STATUS_INACTIVE &&
                sessionStatus.data != API_SESSION_STATUS_LOBBY
            ) {
                userSessionManager.saveSessionId(selectedSession.sessionId)

                return Resource.Success(Unit)
            }

            val response = diplomaExamApi.setSessionStatus(
                "Bearer ${userSessionManager.getSessionToken()}",
                SetSessionStatusRequestDto(selectedSession.sessionId, API_SESSION_STATUS_LOBBY)
            )

            return when (response.code()) {
                ResponseCode.OK.codeInt -> {
                    if (response.body() == null) {
                        Resource.Failure(Error.UnknownError)
                    } else {
                        val confirmedSelectedSession = response.body()!!

                        userSessionManager.saveSessionId(confirmedSelectedSession.id)

                        Resource.Success(Unit)
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

    private suspend fun getSessionStatus(sessionId: Int): Resource<String> {
        try {
            val response = diplomaExamApi.getSessionState(
                "Bearer ${userSessionManager.getSessionToken()}",
                sessionId
            )

            return when (response.code()) {
                ResponseCode.OK.codeInt -> {
                    if (response.body() == null) {
                        Resource.Failure(Error.UnknownError)
                    } else {
                        Resource.Success(response.body()!!.status)
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