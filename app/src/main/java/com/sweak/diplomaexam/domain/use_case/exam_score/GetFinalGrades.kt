package com.sweak.diplomaexam.domain.use_case.exam_score

import com.sweak.diplomaexam.domain.DUMMY_COURSE_OF_STUDIES_GRADE
import com.sweak.diplomaexam.domain.DUMMY_DIPLOMA_EXAM_GRADE
import com.sweak.diplomaexam.domain.DUMMY_THESIS_GRADE
import com.sweak.diplomaexam.domain.DUMMY_THESIS_PRESENTATION_GRADE
import com.sweak.diplomaexam.domain.common.Resource
import com.sweak.diplomaexam.domain.model.exam_score.ExamScoreState
import com.sweak.diplomaexam.domain.model.common.Grade
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.math.RoundingMode
import javax.inject.Inject

class GetFinalGrades @Inject constructor() {

    operator fun invoke() = flow<Resource<ExamScoreState>> {
        delay(1000)

        DUMMY_DIPLOMA_EXAM_GRADE = Grade.values()[
                ((DUMMY_DIPLOMA_EXAM_GRADE.ordinal
                        + DUMMY_THESIS_PRESENTATION_GRADE.ordinal)
                        / 2f)
                    .toBigDecimal().setScale(0, RoundingMode.HALF_EVEN).toInt()
        ]

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
