package com.sweak.diplomaexam.domain.use_case.questions_draw

import com.sweak.diplomaexam.common.*
import com.sweak.diplomaexam.domain.model.ExamQuestion
import com.sweak.diplomaexam.domain.model.QuestionsDrawState
import com.sweak.diplomaexam.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetQuestionsDrawState @Inject constructor() {

    private val questions = listOf(
        ExamQuestion(
            number = 1,
            question = "Wymień cechy algorytmu i sposoby jego reprezentacji.",
            answer = null
        ),
        ExamQuestion(
            number = 2,
            question = "Techniki modelowania bazy danych, diagramy E/R i UML, narzędzia do modelowania.",
            answer = null
        ),
        ExamQuestion(
            number = 3,
            question = "Wymień standardowe metody kompresji sygnałów multimedialnych: obrazów nieruchomych, dźwięku, video. Omów dokładniej jedną z nich.",
            answer = null
        ),
    )

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
        while (true) {
            delay(5000)
            emit(
                Resource.Success(
                    QuestionsDrawState(
                        currentUser = User(DUMMY_GLOBAL_USER_ROLE, DUMMY_GLOBAL_USER_EMAIL),
                        otherUser = User(
                            DUMMY_GLOBAL_OTHER_USER_ROLE,
                            DUMMY_GLOBAL_OTHER_USER_EMAIL
                        ),
                        questions = if (DUMMY_GLOBAL_USER_ROLE == UserRole.USER_EXAMINER) {
                            questions
                        } else {
                            if (DUMMY_GLOBAL_HAVE_QUESTIONS_BEEN_DRAWN) {
                                questions
                            } else {
                                null
                            }
                        }
                    )
                )
            )
        }
    }
}
