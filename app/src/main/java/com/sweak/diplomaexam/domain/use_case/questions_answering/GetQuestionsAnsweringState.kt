package com.sweak.diplomaexam.domain.use_case.questions_answering

import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.domain.repository.QuestionsAnsweringRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetQuestionsAnsweringState @Inject constructor(
    private val repository: QuestionsAnsweringRepository
) {
    operator fun invoke() = flow {
        emit(Resource.Loading())

        while (true) {
            delay(500)

            when (val questionsAnsweringState = repository.getQuestionsAnsweringState()) {
                is Resource.Success -> {
                    emit(Resource.Success(questionsAnsweringState.data))

                    val currentUser = questionsAnsweringState.data?.currentUser

                    if (currentUser != null) {
                        if (currentUser.role == UserRole.USER_STUDENT &&
                            questionsAnsweringState.data.isWaitingForStudentReadiness
                        ) {
                            break
                        } else if (currentUser.role == UserRole.USER_EXAMINER &&
                            (!questionsAnsweringState.data.isWaitingForStudentReadiness ||
                                    questionsAnsweringState.data.isWaitingForFinalEvaluation)
                        ) {
                            break
                        }
                    }
                }
                else -> {
                    emit(Resource.Failure(questionsAnsweringState.error!!))
                    break
                }
            }

            delay(2500)
        }
    }
}