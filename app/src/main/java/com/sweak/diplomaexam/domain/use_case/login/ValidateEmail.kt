package com.sweak.diplomaexam.domain.use_case.login

import java.util.regex.Pattern
import javax.inject.Inject

class ValidateEmail @Inject constructor() {

    private val validationEmailPattern = Pattern.compile(
        "^[a-z]+\\.[a-z]+(-[a-z]+)?@(student\\.)?pk\\.edu\\.pl\$"
    )

    operator fun invoke(email: String): Boolean {
        if (email.isBlank()) {
            return false
        }

        if (!validationEmailPattern.matcher(email).matches()) {
            return false
        }

        return true
    }
}
