package com.sweak.diplomaexam.domain.use_case.login

import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.Error
import com.sweak.diplomaexam.domain.repository.DiplomaExamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticateUser @Inject constructor(
    private val repository: DiplomaExamRepository
) {

    operator fun invoke(email: String, password: String): Flow<Resource<AuthenticationResult>> =
        flow {
            emit(Resource.Loading())

            when (val helloResource = repository.getHello()) {
                is Resource.Loading ->
                    emit(Resource.Loading())
                is Resource.Success -> {
                    if (helloResource.data == "Hello World!") {
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
                            helloResource.error!!,
                            data = AuthenticationResult(
                                successful = false,
                                error = helloResource.error
                            )
                        )
                    )
            }
        }
}

data class AuthenticationResult(
    val successful: Boolean,
    val error: Error? = null
)