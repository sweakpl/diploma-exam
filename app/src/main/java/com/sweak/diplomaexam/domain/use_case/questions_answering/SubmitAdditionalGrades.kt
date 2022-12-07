package com.sweak.diplomaexam.domain.use_case.questions_answering

import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.Error
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.domain.repository.QuestionsAnsweringRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SubmitAdditionalGrades @Inject constructor(
    private val repository: QuestionsAnsweringRepository
) {
    operator fun invoke(
        thesisPresentationGrade: Grade?,
        thesisGrade: Grade?,
        courseOfStudiesPreciseGradeString: String?
    ) = flow {
        emit(Resource.Loading())

        delay(500)

        if (thesisPresentationGrade != null &&
            thesisGrade != null &&
            courseOfStudiesPreciseGradeString != null
        ) {
            when (val submitGradesResponse =
                repository.submitAdditionalGrades(
                    thesisGrade = thesisGrade,
                    thesisPresentationGrade = thesisPresentationGrade,
                    courseOfStudiesPreciseGradeString = courseOfStudiesPreciseGradeString
                )
            ) {
                is Resource.Success -> emit(Resource.Success(Unit))
                else -> emit(Resource.Failure(submitGradesResponse.error!!))
            }
        } else {
            emit(Resource.Failure(Error.UnknownError))
        }
    }
}