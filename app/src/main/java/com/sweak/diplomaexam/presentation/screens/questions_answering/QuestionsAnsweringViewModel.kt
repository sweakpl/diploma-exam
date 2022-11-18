package com.sweak.diplomaexam.presentation.screens.questions_answering

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.domain.use_case.questions_answering.ConfirmReadinessToAnswer
import com.sweak.diplomaexam.domain.use_case.questions_answering.GetQuestionsAnsweringState
import com.sweak.diplomaexam.domain.use_case.questions_answering.SubmitAdditionalGrades
import com.sweak.diplomaexam.domain.use_case.questions_answering.SubmitQuestionGrades
import com.sweak.diplomaexam.presentation.screens.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class QuestionsAnsweringViewModel @Inject constructor(
    private val getQuestionsAnsweringState: GetQuestionsAnsweringState,
    private val confirmReadinessToAnswer: ConfirmReadinessToAnswer,
    private val submitQuestionGrades: SubmitQuestionGrades,
    private val submitAdditionalGrades: SubmitAdditionalGrades
) : ViewModel() {

    var state by mutableStateOf(QuestionsAnsweringScreenState())

    private val gradingCompletedEventsChannel = Channel<GradingCompletedEvent>()
    val gradingCompletedEvents = gradingCompletedEventsChannel.receiveAsFlow()

    private var lastUnsuccessfulOperation: Runnable? = null

    init {
        fetchQuestionsAnsweringState()
    }

    private fun fetchQuestionsAnsweringState() {
        getQuestionsAnsweringState().onEach {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        if (it.data.isGradingCompleted) {
                            gradingCompletedEventsChannel.send(GradingCompletedEvent)
                        } else {
                            state = state.copy(
                                currentUser = it.data.currentUser,
                                otherUser = it.data.otherUser,
                                questions = it.data.questions,
                                isLoadingResponse = false,
                                isWaitingForStudentReadiness = it.data.isWaitingForStudentReadiness,
                                isWaitingForFinalEvaluation = it.data.isWaitingForFinalEvaluation
                            )
                        }
                    }
                }
                is Resource.Loading -> {
                    state = if (state.currentUser == null) {
                        state.copy(currentUser = null)
                    } else {
                        state.copy(isLoadingResponse = true)
                    }
                }
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        fetchQuestionsAnsweringState()
                    }
                    state = state.copy(errorMessage = getErrorMessage(it.error))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: QuestionsAnsweringScreenEvent) {
        when (event) {
            is QuestionsAnsweringScreenEvent.ConfirmReadinessToAnswer -> confirmReadiness()
            is QuestionsAnsweringScreenEvent.HidePreparationDialog ->
                state = state.copy(studentPreparationDialogVisible = false)
            is QuestionsAnsweringScreenEvent.SelectQuestionGrade -> {
                val newQuestionNumbersToGradesMap =
                    state.questionNumbersToGradesMap.toMutableMap().apply {
                        this[event.questionNumber] = event.grade
                    }

                state = state.copy(
                    questionNumbersToGradesMap = newQuestionNumbersToGradesMap
                )
            }
            is QuestionsAnsweringScreenEvent.ProceedClick -> {
                if (!state.isWaitingForFinalEvaluation) {
                    state.questions.forEach {
                        if (!state.questionNumbersToGradesMap.containsKey(it.number) ||
                            state.questionNumbersToGradesMap[it.number] !is Grade
                        ) {
                            state = state.copy(cannotSubmitGradesDialogVisible = true)
                            return
                        }
                    }

                    state = state.copy(submitQuestionGradesDialogVisible = true)
                } else {
                    state = if (state.thesisGrade == null ||
                        state.courseOfStudiesGrade == null ||
                        state.thesisPresentationGrade == null
                    ) {
                        state.copy(cannotSubmitGradesDialogVisible = true)
                    } else {
                        state.copy(submitAdditionalGradesDialogVisible = true)
                    }
                }
            }
            is QuestionsAnsweringScreenEvent.HideCannotSubmitGradesDialog ->
                state = state.copy(cannotSubmitGradesDialogVisible = false)
            is QuestionsAnsweringScreenEvent.HideSubmitQuestionGradesDialog ->
                state = state.copy(submitQuestionGradesDialogVisible = false)
            is QuestionsAnsweringScreenEvent.SubmitQuestionGrades -> submitGradesForQuestions()
            is QuestionsAnsweringScreenEvent.SelectThesisPresentationGrade ->
                state = state.copy(thesisPresentationGrade = event.grade)
            is QuestionsAnsweringScreenEvent.SelectThesisGrade ->
                state = state.copy(thesisGrade = event.grade)
            is QuestionsAnsweringScreenEvent.SelectCourseOfStudiesGrade ->
                state = state.copy(courseOfStudiesGrade = event.grade)
            is QuestionsAnsweringScreenEvent.SubmitAdditionalGrades -> submitGradesForAdditional()
            is QuestionsAnsweringScreenEvent.HideSubmitAdditionalGradesDialog ->
                state = state.copy(submitAdditionalGradesDialogVisible = false)
            is QuestionsAnsweringScreenEvent.RetryAfterError -> {
                state = state.copy(errorMessage = null)

                lastUnsuccessfulOperation?.run()
                lastUnsuccessfulOperation = null
            }
        }
    }

    private fun confirmReadiness() {
        confirmReadinessToAnswer().onEach {
            when (it) {
                is Resource.Success -> fetchQuestionsAnsweringState()
                is Resource.Loading -> state = state.copy(isLoadingResponse = true)
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        confirmReadiness()
                    }
                    state = state.copy(errorMessage = getErrorMessage(it.error))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun submitGradesForQuestions() {
        submitQuestionGrades(state.questionNumbersToGradesMap.values.toList()).onEach {
            when (it) {
                is Resource.Success -> fetchQuestionsAnsweringState()
                is Resource.Loading -> state = state.copy(isLoadingResponse = true)
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        submitGradesForQuestions()
                    }
                    state = state.copy(errorMessage = getErrorMessage(it.error))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun submitGradesForAdditional() {
        submitAdditionalGrades(
            state.thesisPresentationGrade,
            state.thesisGrade,
            state.courseOfStudiesGrade
        ).onEach {
            when (it) {
                is Resource.Success -> fetchQuestionsAnsweringState()
                is Resource.Loading -> state = state.copy(isLoadingResponse = true)
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        submitGradesForAdditional()
                    }
                    state = state.copy(errorMessage = getErrorMessage(it.error))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getErrorMessage(error: Error?): UiText =
        when (error) {
            is Error.IOError -> UiText.StringResource(R.string.cant_reach_server)
            is Error.HttpError -> {
                if (error.message != null)
                    UiText.DynamicString(error.message)
                else
                    UiText.StringResource(R.string.unknown_error)
            }
            is Error.UnauthorizedError ->
                UiText.StringResource(R.string.no_permission)
            else -> UiText.StringResource(R.string.unknown_error)
        }

    object GradingCompletedEvent
}