package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.data.common.ResponseCode
import com.sweak.diplomaexam.data.local.UserSessionManager
import com.sweak.diplomaexam.data.remote.API_ROLE_EXAMINER
import com.sweak.diplomaexam.data.remote.API_ROLE_STUDENT
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.data.remote.dto.login.LoginRequestDto
import com.sweak.diplomaexam.domain.model.login.LoginResponse
import com.sweak.diplomaexam.domain.repository.AuthenticationRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val diplomaExamApi: DiplomaExamApi,
    private val userSessionManager: UserSessionManager
): AuthenticationRepository {

    override suspend fun login(
        email: String,
        password: String,
        selectedUserRole: UserRole
    ): Resource<LoginResponse> {
        try {
            val responseDto = diplomaExamApi.login(LoginRequestDto(email, password))

            return when (responseDto.code()) {
                ResponseCode.OK.codeInt -> {
                    if (responseDto.body() == null) {
                        Resource.Failure(Error.UnknownError)
                    } else {
                        val loginResponse = responseDto.body()!!.run {
                            LoginResponse(
                                email = this.email,
                                role = this.role,
                                token = this.token,
                                sessionId = this.sessionId,
                                expirationDate = this.expirationDate
                            )
                        }

                        val responseUserRole = when(loginResponse.role) {
                            API_ROLE_STUDENT -> UserRole.USER_STUDENT
                            API_ROLE_EXAMINER -> UserRole.USER_EXAMINER
                            else -> null
                        }

                        if (responseUserRole == selectedUserRole) {
                            if (selectedUserRole == UserRole.USER_STUDENT &&
                                loginResponse.sessionId == null
                            ) {
                                return Resource.Failure(Error.UnknownError)
                            }

                            userSessionManager.apply {
                                saveSessionToken(loginResponse.token)
                                saveSessionExpiryDate(loginResponse.expirationDate)
                                saveSessionId(loginResponse.sessionId ?: -1)
                                saveUserRole(responseUserRole.toString())
                                saveUserEmail(loginResponse.email)
                            }

                            Resource.Success(loginResponse)
                        } else {
                            Resource.Failure(Error.WrongUserRoleError(selectedUserRole))
                        }
                    }
                }
                ResponseCode.UNAUTHORIZED.codeInt ->
                    Resource.Failure(Error.UnauthorizedError(responseDto.message()))
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