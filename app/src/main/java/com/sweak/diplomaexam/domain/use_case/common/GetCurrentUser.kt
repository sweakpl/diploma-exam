package com.sweak.diplomaexam.domain.use_case.common

import com.sweak.diplomaexam.common.DUMMY_GLOBAL_USER_EMAIL
import com.sweak.diplomaexam.common.DUMMY_GLOBAL_USER_ROLE
import com.sweak.diplomaexam.common.Resource
import com.sweak.diplomaexam.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentUser @Inject constructor() {

    // Emitting dummy data to test app behavior
    operator fun invoke(): Flow<Resource<User>> =
        flow {
            delay(2500)
            emit(Resource.Success(User(DUMMY_GLOBAL_USER_ROLE, DUMMY_GLOBAL_USER_EMAIL)))
        }
}