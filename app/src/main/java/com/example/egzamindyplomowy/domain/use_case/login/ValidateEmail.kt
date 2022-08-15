package com.example.egzamindyplomowy.domain.use_case.login

import com.example.egzamindyplomowy.R
import com.example.egzamindyplomowy.presentation.UiText
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateEmail @Inject constructor() {

    private val validationEmailPattern = Pattern.compile(
        "^[a-z]+\\.[a-z]+(-[a-z]+)?@(student\\.)?pk\\.edu\\.pl\$"
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