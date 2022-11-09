package com.sweak.diplomaexam.domain

import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.domain.model.common.UserRole

// Dummy state variables - USED ONLY TO SIMULATE APP BEHAVIOR!
lateinit var DUMMY_USER_ROLE: UserRole
lateinit var DUMMY_USER_EMAIL: String
lateinit var DUMMY_OTHER_USER_ROLE: UserRole
lateinit var DUMMY_OTHER_USER_EMAIL: String
var DUMMY_HAVE_QUESTIONS_BEEN_DRAWN: Boolean = false
var DUMMY_HAS_STUDENT_REQUESTED_REDRAW: Boolean = false
var DUMMY_HAS_EXAMINER_ALLOWED_REDRAW: Boolean = false
var DUMMY_ARE_QUESTIONS_CONFIRMED: Boolean = false
var DUMMY_DRAWN_QUESTIONS: List<ExamQuestion> = emptyList()
var DUMMY_IS_STUDENT_READY_TO_ANSWER = false
var DUMMY_ARE_QUESTION_GRADES_CONFIRMED = false
var DUMMY_ARE_ADDITIONAL_GRADES_CONFIRMED = false
var DUMMY_DIPLOMA_EXAM_GRADE: Grade = Grade.C
var DUMMY_THESIS_GRADE: Grade = Grade.E
var DUMMY_COURSE_OF_STUDIES_GRADE: Grade = Grade.D