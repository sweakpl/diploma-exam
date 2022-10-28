package com.sweak.diplomaexam.data.remote.model

data class AvailableSession(
    val canRedrawQuestions: Boolean,
    val examinationDate: String,
    val hasExaminerJoined: Boolean,
    val hasStudentJoined: Boolean,
    val id: Int,
    val status: String,
    val userDtos: List<UserDto>
)