package com.sweak.diplomaexam.domain.use_case.login

import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.common.Resource
import com.sweak.diplomaexam.domain.repository.DiplomaExamRepository
import com.sweak.diplomaexam.presentation.ui.util.UiText
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
                                    errorMessage = UiText.StringResource(R.string.unknown_error)
                                )
                            )
                        )
                    }
                }
                is Resource.Error ->
                    emit(
                        Resource.Error(
                            message = helloResource.message
                                ?: UiText.StringResource(R.string.unknown_error),
                            data = AuthenticationResult(
                                successful = false,
                                errorMessage = helloResource.message
                                    ?: UiText.StringResource(R.string.unknown_error)
                            )
                        )
                    )
            }
        }
}

data class AuthenticationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)