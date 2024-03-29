package com.sweak.diplomaexam.data.remote.dto.session

data class SessionStateDto(
    val id: Int,
    val status: String,
    val userDtos: List<UserDto>,
    val hasExaminerJoined: Boolean,
    val hasStudentJoined: Boolean,
    val haveQuestionsBeenDrawn: Boolean,
    val haveQuestionsBeenRedrawn: Boolean,
    val haveStudentRequestedRedraw: Boolean,
    val areQuestionsBeingGraded: Boolean,
    val isExamBeingSummarized: Boolean
)