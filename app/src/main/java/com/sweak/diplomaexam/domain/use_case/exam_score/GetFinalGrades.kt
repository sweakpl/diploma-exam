package com.sweak.diplomaexam.domain.use_case.exam_score

import com.sweak.diplomaexam.common.DUMMY_COURSE_OF_STUDIES_GRADE
import com.sweak.diplomaexam.common.DUMMY_DIPLOMA_EXAM_GRADE
import com.sweak.diplomaexam.common.DUMMY_THESIS_GRADE
import com.sweak.diplomaexam.common.Resource
import com.sweak.diplomaexam.domain.model.ExamScoreState
import com.sweak.diplomaexam.domain.model.Grade
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.math.RoundingMode
import javax.inject.Inject

class GetFinalGrades @Inject constructor() {

    operator fun invoke() = flow<Resource<ExamScoreState>> {
        delay(1000)

        val finalGrade = Grade.values()[
                ((DUMMY_DIPLOMA_EXAM_GRADE.ordinal
                        + DUMMY_THESIS_GRADE.ordinal
                        + DUMMY_COURSE_OF_STUDIES_GRADE.ordinal)
                        / 3f)
                    .toBigDecimal().setScale(0, RoundingMode.HALF_EVEN).toInt()
        ]

        emit(
            Resource.Success(
                ExamScoreState(
                    finalGrade = finalGrade,
                    diplomaExamGrade = DUMMY_DIPLOMA_EXAM_GRADE,
                    thesisGrade = DUMMY_THESIS_GRADE,
                    courseOfStudiesGrade = DUMMY_COURSE_OF_STUDIES_GRADE
                )
            )
        )
    }
}
