package com.example.egzamindyplomowy.domain.use_case.login

import com.example.egzamindyplomowy.R
import com.example.egzamindyplomowy.presentation.UiText
import java.util.regex.Pattern

class ValidateEmail {

    private val validationEmailPattern = Pattern.compile(
        "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*\$"
    )

    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_email_error)
            )
        }

        if (!validationEmailPattern.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_email_error)
            )
        }

        return ValidationResult(successful = true)
    }

    data class ValidationResult(
        val successful: Boolean,
        val errorMessage: UiText? = null
    )
}