package com.sweak.diplomaexam.domain.use_case.exam_score

import com.sweak.diplomaexam.domain.repository.ExamScoreRepository
import javax.inject.Inject

class CleanupSession @Inject constructor(
    private val repository: ExamScoreRepository
) {
    operator fun invoke() {
        repository.finishExam()
    }
}