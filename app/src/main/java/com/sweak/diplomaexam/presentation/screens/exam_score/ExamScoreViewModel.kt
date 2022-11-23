package com.sweak.diplomaexam.presentation.screens.exam_score

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.use_case.exam_score.CleanupSession
import com.sweak.diplomaexam.domain.use_case.exam_score.GetExamScoreState
import com.sweak.diplomaexam.presentation.screens.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamScoreViewModel @Inject constructor(
    private val getExamScoreState: GetExamScoreState,
    private val cleanupSession: CleanupSession
): ViewModel() {

    var state by mutableStateOf(ExamScoreScreenState())

    private val examFinishedEventsChannel = Channel<ExamFinishedEvent>()
    val examFinishedEvents = examFinishedEventsChannel.receiveAsFlow()

    private var lastUnsuccessfulOperation: Runnable? = null

    init {
        fetchExamScoreState()
    }

    private fun fetchExamScoreState() {
        getExamScoreState().onEach {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        state = state.copy(
                            isLoadingResponse = false,
                            finalGrade = it.data.finalGrade,
                            diplomaExamGrade = it.data.diplomaExamGrade,
                            thesisGrade = it.data.thesisGrade,
                            courseOfStudiesGrade = it.data.courseOfStudiesGrade
                        )
                    }
                }
                is Resource.Loading -> {
                    state = state.copy(isLoadingResponse = true)
                }
                is Resource.Failure -> {
                    lastUnsuccessfulOperation = Runnable {
                        fetchExamScoreState()
                    }
                    state = state.copy(errorMessage = getErrorMessage(it.error))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ExamScoreScreenEvent) {
        when (event) {
            is ExamScoreScreenEvent.FinishExam -> finishExam()
            is ExamScoreScreenEvent.RetryAfterError -> {
                state = state.copy(errorMessage = null)

                lastUnsuccessfulOperation?.run()
                lastUnsuccessfulOperation = null
            }
        }
    }

    private fun finishExam() = viewModelScope.launch {
        cleanupSession()
        examFinishedEventsChannel.send(ExamFinishedEvent)
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

    object ExamFinishedEvent
}