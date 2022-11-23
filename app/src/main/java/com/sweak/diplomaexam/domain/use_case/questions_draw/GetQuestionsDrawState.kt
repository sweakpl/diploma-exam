package com.sweak.diplomaexam.domain.use_case.questions_draw

import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.repository.QuestionsDrawRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetQuestionsDrawState @Inject constructor(
    private val repository: QuestionsDrawRepository
) {
    operator fun invoke() = flow {
        emit(Resource.Loading())

        while (true) {
            delay(500)

            when (val questionsDrawState = repository.getQuestionsDrawState()) {
                is Resource.Success -> {
                    emit(Resource.Success(questionsDrawState.data))

                    if (questionsDrawState.data?.currentUser != null) {
                        if (questionsDrawState.data.currentUser.role ==
                            questionsDrawState.data.waitingForDecisionFrom
                        ) {
                            break
                        }
                    }
                }
                else -> {
                    emit(Resource.Failure(questionsDrawState.error!!))
                    break
                }
            }

            delay(2500)
        }
    }
}
