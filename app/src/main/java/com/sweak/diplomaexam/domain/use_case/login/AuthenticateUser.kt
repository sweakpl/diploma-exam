package com.sweak.diplomaexam.domain.use_case.login

import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.domain.repository.AuthenticationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticateUser @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(
        email: String,
        password: String,
        selectedUserRole: UserRole
    ): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading())

            delay(500)

            when (val loginResponse = repository.login(email, password, selectedUserRole)) {
                is Resource.Success -> emit(Resource.Success(Unit))
                else -> emit(Resource.Failure(loginResponse.error!!))
            }
        }
}