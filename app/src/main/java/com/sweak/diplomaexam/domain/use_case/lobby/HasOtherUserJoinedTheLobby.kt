package com.sweak.diplomaexam.domain.use_case.lobby

import com.sweak.diplomaexam.common.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HasOtherUserJoinedTheLobby @Inject constructor() {

    // Emitting dummy data to test app behavior
    operator fun invoke() = flow<Resource<Boolean>> {
        delay(5000)
        emit(Resource.Success(true))
    }
}