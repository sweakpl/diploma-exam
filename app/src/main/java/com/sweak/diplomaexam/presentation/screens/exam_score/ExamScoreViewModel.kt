package com.sweak.diplomaexam.presentation.screens.exam_score

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.use_case.exam_score.CleanupSession
import com.sweak.diplomaexam.domain.use_case.exam_score.GetFinalGrades
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamScoreViewModel @Inject constructor(
    getFinalGrades: GetFinalGrades,
    private val cleanupSession: CleanupSession
): ViewModel() {

    var state by mutableStateOf(ExamScoreScreenState())

    private val examFinishedEventsChannel = Channel<ExamFinishedEvent>()
    val examFinishedEvents = examFinishedEventsChannel.receiveAsFlow()

    init {
        getFinalGrades().onEach {
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
                else -> { /* no-op */ }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ExamScoreScreenEvent) {
        when (event) {
            is ExamScoreScreenEvent.FinishExam -> finishExam()
        }
    }

    private fun finishExam() = viewModelScope.launch {
        cleanupSession()
        examFinishedEventsChannel.send(ExamFinishedEvent)
    }

    object ExamFinishedEvent
}