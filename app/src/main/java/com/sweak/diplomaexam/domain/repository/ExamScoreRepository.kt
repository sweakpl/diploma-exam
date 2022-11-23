package com.sweak.diplomaexam.domain.repository

import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.exam_score.ExamScoreState

interface ExamScoreRepository {

    suspend fun getExamScoreState(): Resource<ExamScoreState>
    fun finishExam()
}