package com.sweak.diplomaexam.domain.repository

import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.domain.model.questions_draw.QuestionsDrawState

interface QuestionsDrawRepository {

    suspend fun getQuestionsDrawState(): Resource<QuestionsDrawState>
    suspend fun getQuestions(): Resource<List<ExamQuestion>>
    suspend fun requestQuestionsRedraw(): Resource<Unit>
    suspend fun redrawQuestions(): Resource<Unit>
    suspend fun acceptDrawnQuestions(): Resource<Unit>
}