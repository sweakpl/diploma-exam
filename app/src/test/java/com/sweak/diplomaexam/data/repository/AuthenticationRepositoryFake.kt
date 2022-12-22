package com.sweak.diplomaexam.data.repository

import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.domain.model.login.LoginResponse
import com.sweak.diplomaexam.domain.repository.AuthenticationRepository

class AuthenticationRepositoryFake : AuthenticationRepository {

    var isSuccessfulResponse: Boolean = true

    override suspend fun login(
        email: String,
        password: String,
        selectedUserRole: UserRole
    ): Resource<LoginResponse> {
        return if (isSuccessfulResponse) {
            Resource.Success(
                LoginResponse(
                    email,
                    selectedUserRole.name,
                    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LnN0dWRlbnRAcGsuZWR1LnBsIiwicm9sZS",
                    43,
                    "2023-03-01T20:00:00.000+00:00"
                )
            )
        } else {
            Resource.Failure(Error.UnknownError)
        }
    }
}