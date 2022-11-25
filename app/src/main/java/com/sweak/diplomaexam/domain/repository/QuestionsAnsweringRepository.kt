package com.sweak.diplomaexam.domain.repository

import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.domain.model.questions_answering.QuestionsAnsweringState

interface QuestionsAnsweringRepository {

    suspend fun getQuestionsAnsweringState(): Resource<QuestionsAnsweringState>
    suspend fun getQuestions(): Resource<List<ExamQuestion>>
    suspend fun confirmReadinessToAnswer(): Resource<Unit>
    suspend fun submitQuestionGrades(questionsToGradesMap: Map<ExamQuestion, Grade>): Resource<Unit>
    suspend fun submitAdditionalGrades(
        thesisGrade: Grade,
        thesisPresentationGrade: Grade,
        courseOfStudiesGrade: Grade
    ): Resource<Unit>
}