package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.data.common.ResponseCode
import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.remote.API_SESSION_STATUS_DRAWING_QUESTIONS
import com.sweak.diplomaexam.data.remote.API_SESSION_STATUS_INACTIVE
import com.sweak.diplomaexam.data.remote.API_SESSION_STATUS_LOBBY
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.data.remote.dto.session.SetSessionStateRequestDto
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.User
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.domain.model.lobby.LobbyState
import com.sweak.diplomaexam.domain.repository.LobbyRepository
import retrofit2.HttpException
import java.io.IOException

class LobbyRepositoryImpl(
    private val diplomaExamApi: DiplomaExamApi,
    private val userSessionManager: UserSessionManager
) : LobbyRepository {

    override suspend fun getLobbyState(): Resource<LobbyState> {
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
                        val currentUser: User

                        try {
                            currentUser = User(
                                UserRole.valueOf(userSessionManager.getUserRole() ?: ""),
                                userSessionManager.getUserEmail()
                            )
                        } catch (illegalArgumentException: IllegalArgumentException) {
                            return Resource.Failure(Error.UnauthorizedError(null))
                        }

                        Resource.Success(
                            LobbyState(
                                currentUser = currentUser,
                                hasOtherUserJoinedTheLobby =
                                if (currentUser.role == UserRole.USER_EXAMINER)
                                    sessionState.hasStudentJoined
                                else
                                    sessionState.hasExaminerJoined,
                                hasTheSessionBeenStarted = sessionState.status.run {
                                    this != API_SESSION_STATUS_LOBBY &&
                                            this != API_SESSION_STATUS_INACTIVE
                                }
                            )
                        )
                    }
                }
                ResponseCode.UNAUTHORIZED.codeInt ->
                    Resource.Failure(Error.UnauthorizedError(response.message()))
                else -> Resource.Failure(Error.UnknownError)
            }
        }  catch (httpException: HttpException) {
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

    override suspend fun startExaminationSession(): Resource<Unit> {
        try {
            val response = diplomaExamApi.setSessionState(
                "Bearer ${userSessionManager.getSessionToken()}",
                SetSessionStateRequestDto(
                    userSessionManager.getSessionId(),
                    API_SESSION_STATUS_DRAWING_QUESTIONS
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