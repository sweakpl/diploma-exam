package com.sweak.diplomaexam.domain.repository

import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.domain.model.questions_answering.QuestionsAnsweringState

interface QuestionsAnsweringRepository {

    suspend fun getQuestionsAnsweringState(): Resource<QuestionsAnsweringState>
    suspend fun getQuestions(): Resource<List<ExamQuestion>>
    suspend fun confirmReadinessToAnswer(): Resource<Unit>
    suspend fun submitQuestionGrades(gradesList: List<Grade>): Resource<Unit>
    suspend fun submitAdditionalGrades(
        thesisGrade: Grade,
        thesisPresentationGrade: Grade,
        courseOfStudiesGrade: Grade
    ): Resource<Unit>
}