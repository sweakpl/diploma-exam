package com.sweak.diplomaexam.domain.use_case.lobby

import com.sweak.diplomaexam.common.DUMMY_HAS_SESSION_BEEN_STARTED
import kotlinx.coroutines.delay
import javax.inject.Inject

class StartExamSession @Inject constructor() {

    suspend operator fun invoke() {
        delay(1000)
        DUMMY_HAS_SESSION_BEEN_STARTED = true
    }
}