package com.sweak.diplomaexam.domain.use_case.questions_draw

import com.sweak.diplomaexam.common.*
import com.sweak.diplomaexam.domain.model.User
import com.sweak.diplomaexam.presentation.questions_draw.QuestionsDrawState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetQuestionsDrawState @Inject constructor() {

    // Emitting dummy data to test app behavior
    operator fun invoke() = flow<Resource<QuestionsDrawState>> {
        delay(3000)
        emit(
            Resource.Success(
                QuestionsDrawState(
                    currentUser = User(DUMMY_GLOBAL_USER_ROLE, DUMMY_GLOBAL_USER_EMAIL),
                    otherUser = User(DUMMY_GLOBAL_OTHER_USER_ROLE, DUMMY_GLOBAL_OTHER_USER_EMAIL)
                )
            )
        )
    }
}
