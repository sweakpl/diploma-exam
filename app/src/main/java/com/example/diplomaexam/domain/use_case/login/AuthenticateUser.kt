package com.example.diplomaexam.domain.use_case.login

import com.example.diplomaexam.R
import com.example.diplomaexam.domain.repository.DiplomaExamRepository
import com.example.diplomaexam.presentation.UiText
import javax.inject.Inject

class AuthenticateUser @Inject constructor(
    private val repository: DiplomaExamRepository
) {

    suspend operator fun invoke(email: String, password: String): AuthenticationResult {
        val hello = repository.getHello()

        return if (hello == "Hello World!") {
            AuthenticationResult(successful = true)
        } else {
            AuthenticationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.wrong_credentials_error)
            )
        }
    }

    data class AuthenticationResult(
        val successful: Boolean,
        val errorMessage: UiText? = null
    )
}