package com.sweak.diplomaexam.domain.use_case.login

import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.login.LoginRequest
import com.sweak.diplomaexam.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticateUser @Inject constructor(
    private val repository: AuthenticationRepository
) {

    operator fun invoke(email: String, password: String): Flow<Resource<AuthenticationResult>> =
        flow {
            emit(Resource.Loading())

            when (val loginResponse = repository.login(LoginRequest(email, password))) {
                is Resource.Success -> {
                    if (loginResponse.data?.accessToken != null) {
                        emit(Resource.Success(AuthenticationResult(successful = true)))
                    } else {
                        emit(
                            Resource.Success(
                                AuthenticationResult(
                                    successful = false,
                                    error = Error.UnknownError
                                )
                            )
                        )
                    }
                }
                is Resource.Failure ->
                    emit(
                        Resource.Failure(
                            loginResponse.error!!,
                            data = AuthenticationResult(
                                successful = false,
                                error = loginResponse.error
                            )
                        )
                    )
                is Resource.Loading -> { /* no-op */ }
            }
        }
}

data class AuthenticationResult(
    val successful: Boolean,
    val error: Error? = null
)