package com.sweak.diplomaexam.common

import com.sweak.diplomaexam.domain.model.ExamQuestion

// Retrofit base URL
const val BASE_URL = "https://diploma-examination-system.herokuapp.com/"

// Dummy state variables - USED ONLY TO SIMULATE APP BEHAVIOR!
lateinit var DUMMY_USER_ROLE: UserRole
lateinit var DUMMY_USER_EMAIL: String
lateinit var DUMMY_OTHER_USER_ROLE: UserRole
lateinit var DUMMY_OTHER_USER_EMAIL: String
var DUMMY_HAS_SESSION_BEEN_STARTED: Boolean = false
var DUMMY_HAVE_QUESTIONS_BEEN_DRAWN: Boolean = false
var DUMMY_HAS_STUDENT_REQUESTED_REDRAW: Boolean = false
var DUMMY_HAS_EXAMINER_ALLOWED_REDRAW: Boolean = false
var DUMMY_ARE_QUESTIONS_CONFIRMED: Boolean = false
var DUMMY_DRAWN_QUESTIONS: List<ExamQuestion> = emptyList()
var DUMMY_IS_STUDENT_READY_TO_ANSWER = false