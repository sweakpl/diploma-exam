package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.data.common.ResponseCode
import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.data.remote.dto.session.SetSessionStateRequestDto
import com.sweak.diplomaexam.domain.common.Resource
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
                                        userDto.role == "STUDENT"
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
            val response = diplomaExamApi.setSessionState(
                "Bearer ${userSessionManager.getSessionToken()}",
                SetSessionStateRequestDto(
                    selectedSession.sessionId,
                    "LOBBY"
                )
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