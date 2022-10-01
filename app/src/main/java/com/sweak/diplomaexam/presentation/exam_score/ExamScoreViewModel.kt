package com.sweak.diplomaexam.presentation.exam_score

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.diplomaexam.common.*
import com.sweak.diplomaexam.domain.model.Grade
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
    getFinalGrades: GetFinalGrades
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

    fun finishExam() = viewModelScope.launch {
        DUMMY_HAS_SESSION_BEEN_STARTED = false
        DUMMY_HAVE_QUESTIONS_BEEN_DRAWN = false
        DUMMY_HAS_STUDENT_REQUESTED_REDRAW = false
        DUMMY_HAS_EXAMINER_ALLOWED_REDRAW = false
        DUMMY_ARE_QUESTIONS_CONFIRMED = false
        DUMMY_DRAWN_QUESTIONS = emptyList()
        DUMMY_IS_STUDENT_READY_TO_ANSWER = false
        DUMMY_ARE_QUESTION_GRADES_CONFIRMED = false
        DUMMY_ARE_ADDITIONAL_GRADES_CONFIRMED = false
        DUMMY_DIPLOMA_EXAM_GRADE = Grade.C
        DUMMY_THESIS_GRADE = Grade.E
        DUMMY_COURSE_OF_STUDIES_GRADE = Grade.D

        examFinishedEventsChannel.send(ExamFinishedEvent())
    }

    class ExamFinishedEvent
}