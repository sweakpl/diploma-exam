package com.sweak.diplomaexam.presentation.questions_answering

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.Grade
import com.sweak.diplomaexam.domain.use_case.questions_answering.ConfirmReadinessToAnswer
import com.sweak.diplomaexam.domain.use_case.questions_answering.GetQuestionsAnsweringState
import com.sweak.diplomaexam.domain.use_case.questions_answering.SubmitAdditionalGrades
import com.sweak.diplomaexam.domain.use_case.questions_answering.SubmitQuestionGrades
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsAnsweringViewModel @Inject constructor(
    getQuestionsAnsweringState: GetQuestionsAnsweringState,
    private val confirmReadinessToAnswer: ConfirmReadinessToAnswer,
    private val submitQuestionGrades: SubmitQuestionGrades,
    private val submitAdditionalGrades: SubmitAdditionalGrades
) : ViewModel() {

    var state by mutableStateOf(QuestionsAnsweringScreenState())

    private val gradingCompletedEventsChannel = Channel<GradingCompletedEvent>()
    val gradingCompletedEvents = gradingCompletedEventsChannel.receiveAsFlow()

    private var hasFinalizedRequest: Boolean = true

    init {
        getQuestionsAnsweringState().onEach {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        if (it.data.isGradingCompleted) {
                            gradingCompletedEventsChannel.send(GradingCompletedEvent.Success)
                        } else {
                            state = state.copy(
                                currentUser = it.data.currentUser,
                                otherUser = it.data.otherUser,
                                questions = it.data.questions,
                                questionNumbersToGradesMap = it.data.questionNumbersToGradesMap,
                                thesisGrade = it.data.thesisGrade,
                                courseOfStudiesGrade = it.data.courseOfStudiesGrade,
                                isLoadingResponse = !hasFinalizedRequest,
                                isWaitingForStudentReadiness = it.data.isWaitingForStudentReadiness,
                                isWaitingForFinalEvaluation = it.data.isWaitingForFinalEvaluation
                            )
                        }
                    }
                }
                else -> { /* no-op */ }
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
                    state = if (state.thesisGrade == null || state.courseOfStudiesGrade == null) {
                        state.copy(cannotSubmitGradesDialogVisible = true)
                    } else {
                        state.copy(submitAdditionalGradesDialogVisible = true)
                    }
                }
            }
            is QuestionsAnsweringScreenEvent.HideCannotSubmitGradesDialog -> {
                state = state.copy(cannotSubmitGradesDialogVisible = false)
            }
            is QuestionsAnsweringScreenEvent.HideSubmitQuestionGradesDialog -> {
                state = state.copy(submitQuestionGradesDialogVisible = false)
            }
            is QuestionsAnsweringScreenEvent.SubmitQuestionGrades -> submitGradesForQuestions()
            is QuestionsAnsweringScreenEvent.SelectThesisGrade ->
                state = state.copy(thesisGrade = event.grade)
            is QuestionsAnsweringScreenEvent.SelectCourseOfStudiesGrade ->
                state = state.copy(courseOfStudiesGrade = event.grade)
            is QuestionsAnsweringScreenEvent.SubmitAdditionalGrades -> submitGradesForAdditional()
            is QuestionsAnsweringScreenEvent.HideSubmitAdditionalGradesDialog ->
                state = state.copy(submitAdditionalGradesDialogVisible = false)
        }
    }

    private fun confirmReadiness() =
        performRequest { viewModelScope.launch { confirmReadinessToAnswer() } }

    private fun submitGradesForQuestions() =
        performRequest {
            viewModelScope.launch {
                submitQuestionGrades(state.questionNumbersToGradesMap.values.toList())
            }
        }

    private fun submitGradesForAdditional() =
        performRequest {
            viewModelScope.launch {
                submitAdditionalGrades(state.thesisGrade, state.courseOfStudiesGrade)
            }
        }

    private fun performRequest(request: () -> Unit) {
        hasFinalizedRequest = false
        state = state.copy(isLoadingResponse = true)
        request()
        hasFinalizedRequest = true
    }

    sealed class GradingCompletedEvent {
        object Success : GradingCompletedEvent()
    }
}