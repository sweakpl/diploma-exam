package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.data.common.ResponseCode
import com.sweak.diplomaexam.data.remote.DiplomaExamApi
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.login.LoginRequest
import com.sweak.diplomaexam.domain.model.login.LoginResponse
import com.sweak.diplomaexam.domain.repository.AuthenticationRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val api: DiplomaExamApi
): AuthenticationRepository {
    override suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse> {
        try {
            val response = api.login(loginRequest)

            return when (response.code()) {
                ResponseCode.OK.codeInt -> Resource.Success(
                    response.body() ?: LoginResponse("", "")
                )
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