package com.sweak.diplomaexam.domain.use_case.questions_draw

import com.sweak.diplomaexam.common.*
import com.sweak.diplomaexam.domain.model.ExamQuestion
import com.sweak.diplomaexam.domain.model.QuestionsDrawState
import com.sweak.diplomaexam.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class GetQuestionsDrawState @Inject constructor() {

    private var state: QuestionsDrawState = QuestionsDrawState()

    private val questions = listOf(
        ExamQuestion(
            number = 1,
            question = "Wymień cechy algorytmu i sposoby jego reprezentacji.",
            answer = "Algorytm jest skończonym, uporządkowanym ciągiem jasno zdefiniowanych czynności, koniecznych do wykonania postawionego zadania. Cechuje się poprawnością, jednoznacznością, skończonością, sprawnością."
        ),
        ExamQuestion(
            number = 2,
            question = "Techniki modelowania bazy danych, diagramy E/R i UML, narzędzia do modelowania.",
            answer = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
        ),
        ExamQuestion(
            number = 3,
            question = "Wymień standardowe metody kompresji sygnałów multimedialnych: obrazów nieruchomych, dźwięku, video. Omów dokładniej jedną z nich.",
            answer = "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        ),
    )

    private val redrawnQuestions = listOf(
        ExamQuestion(
            number = 1,
            question = "Omówić zagadnienie asymptotycznej złożoności obliczeniowej algorytmów. Podać standardowe notacje rzędu złożoności.",
            answer = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
        ),
        ExamQuestion(
            number = 2,
            question = "Problemy współbieżności i wielodostępu w SZBD.",
            answer = "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        ),
        ExamQuestion(
            number = 3,
            question = "Omów podstawowe metody bezstratnej i stratnej kompresji danych.",
            answer = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo."
        ),
    )

    // Emitting dummy data to test app behavior
    operator fun invoke() = flow<Resource<QuestionsDrawState>> {
        delay(3000)

        state = state.copy(
            currentUser = User(DUMMY_USER_ROLE, DUMMY_USER_EMAIL),
            otherUser = User(DUMMY_OTHER_USER_ROLE, DUMMY_OTHER_USER_EMAIL)
        )
        emit(Resource.Success(state))

        if (DUMMY_USER_ROLE == UserRole.USER_STUDENT) {
            while (true) {
                delay(3000)

                if (DUMMY_HAVE_QUESTIONS_BEEN_DRAWN) {
                    state = state.copy(
                        questions = if (DUMMY_HAVE_QUESTIONS_BEEN_DRAWN) questions else null,
                        waitingForDecisionFrom = UserRole.USER_STUDENT
                    )
                    emit(Resource.Success(state))
                    break
                }
            }

            while (true) {
                delay(3000)

                if (DUMMY_ARE_QUESTIONS_CONFIRMED) {
                    DUMMY_DRAWN_QUESTIONS = questions
                    state = state.copy(areQuestionsConfirmed = true)
                    emit(Resource.Success(state))
                    break
                }

                if (DUMMY_HAS_STUDENT_REQUESTED_REDRAW) {
                    state = state.copy(
                        hasStudentRequestedRedraw = true,
                        waitingForDecisionFrom = UserRole.USER_EXAMINER
                    )
                    emit(Resource.Success(state))
                    break
                }
            }

            delay(5000)

            if (Random.nextBoolean()) {
                DUMMY_DRAWN_QUESTIONS = questions
                state = state.copy(areQuestionsConfirmed = true)
                emit(Resource.Success(state))
                return@flow
            } else {
                state = state.copy(
                    questions = redrawnQuestions,
                    waitingForDecisionFrom = UserRole.USER_STUDENT
                )
                emit(Resource.Success(state))
            }

            while (true) {
                delay(3000)

                if (DUMMY_ARE_QUESTIONS_CONFIRMED) {
                    DUMMY_DRAWN_QUESTIONS = redrawnQuestions
                    state = state.copy(areQuestionsConfirmed = true)
                    emit(Resource.Success(state))
                    break
                }
            }
        } else if (DUMMY_USER_ROLE == UserRole.USER_EXAMINER) {
            delay(5000)

            state = state.copy(
                questions = questions,
                waitingForDecisionFrom = UserRole.USER_STUDENT
            )
            emit(Resource.Success(state))

            delay(10000)

            if (Random.nextBoolean()) {
                DUMMY_DRAWN_QUESTIONS = questions
                state = state.copy(areQuestionsConfirmed = true)
                emit(Resource.Success(state))
                return@flow
            } else {
                state = state.copy(
                    hasStudentRequestedRedraw = true,
                    waitingForDecisionFrom = UserRole.USER_EXAMINER
                )
                emit(Resource.Success(state))
            }

            while (true) {
                delay(3000)

                if (DUMMY_ARE_QUESTIONS_CONFIRMED) {
                    DUMMY_DRAWN_QUESTIONS = questions
                    state = state.copy(areQuestionsConfirmed = true)
                    emit(Resource.Success(state))
                    break
                }

                if (DUMMY_HAS_EXAMINER_ALLOWED_REDRAW) {
                    DUMMY_DRAWN_QUESTIONS = redrawnQuestions
                    state = state.copy(
                        questions = redrawnQuestions,
                        waitingForDecisionFrom = UserRole.USER_STUDENT
                    )
                    emit(Resource.Success(state))
                    break
                }
            }

            delay(10000)

            state = state.copy(areQuestionsConfirmed = true)
            emit(Resource.Success(state))
        }
    }
}
