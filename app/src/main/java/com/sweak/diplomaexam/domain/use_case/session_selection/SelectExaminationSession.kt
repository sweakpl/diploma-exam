package com.sweak.diplomaexam.domain.use_case.session_selection

import com.sweak.diplomaexam.domain.DUMMY_IS_SESSION_SELECTION_CONFIRMED
import com.sweak.diplomaexam.domain.DUMMY_OTHER_USER_EMAIL
import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession
import kotlinx.coroutines.delay
import javax.inject.Inject

class SelectExaminationSession @Inject constructor() {

    suspend operator fun invoke(availableSession: AvailableSession) {
        delay(1000)

        DUMMY_OTHER_USER_EMAIL = availableSession.studentEmail
        DUMMY_IS_SESSION_SELECTION_CONFIRMED = true
    }
}