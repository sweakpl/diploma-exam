package com.example.egzamindyplomowy.domain.use_case.login

import com.example.egzamindyplomowy.R
import com.example.egzamindyplomowy.presentation.UiText
import kotlinx.coroutines.delay
import kotlin.random.Random

class AuthenticateUser {

    suspend operator fun invoke(email: String, password: String): AuthenticationResult {
        delay(1000)

        val success = Random.nextBoolean()

        return if (success) {
            AuthenticationResult(successful = success)
        } else {
            AuthenticationResult(
                successful = success,
                errorMessage = UiText.StringResource(R.string.wrong_credentials_error)
            )
        }
    }

    data class AuthenticationResult(
        val successful: Boolean,
        val errorMessage: UiText? = null
    )
}