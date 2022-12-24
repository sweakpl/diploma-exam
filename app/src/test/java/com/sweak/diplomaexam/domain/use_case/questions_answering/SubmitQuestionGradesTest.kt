package com.sweak.diplomaexam.domain.use_case.questions_answering

import com.sweak.diplomaexam.data.repository.QuestionsAnsweringRepositoryFake
import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.domain.model.common.Grade
import com.sweak.diplomaexam.domain.model.common.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SubmitQuestionGradesTest {

    private lateinit var submitQuestionGrades: SubmitQuestionGrades
    private lateinit var questionsAnsweringRepository: QuestionsAnsweringRepositoryFake

    @Before
    fun setUp() {
        questionsAnsweringRepository = QuestionsAnsweringRepositoryFake()
        submitQuestionGrades = SubmitQuestionGrades(questionsAnsweringRepository)
    }

    @Test
    fun `If request is successful, then first value is Loading, second is Success`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = true

        val flowValues = submitQuestionGrades.invoke(
            mapOf(
                ExamQuestion(12, 1, "Questions 1", "Answer 1") to Grade.A,
                ExamQuestion(32, 2, "Questions 2", "Answer 2") to Grade.B,
                ExamQuestion(27, 3, "Questions 3", "Answer 3") to Grade.C
            )
        ).toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues.first()::class.java
        )

        Assert.assertEquals(
            Resource.Success::class.java,
            flowValues[1]::class.java
        )
    }

    @Test
    fun `If request is unsuccessful, then first value is Loading, second is Failure`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = false

        val flowValues = submitQuestionGrades.invoke(
            mapOf(
                ExamQuestion(12, 1, "Questions 1", "Answer 1") to Grade.A,
                ExamQuestion(32, 2, "Questions 2", "Answer 2") to Grade.B,
                ExamQuestion(27, 3, "Questions 3", "Answer 3") to Grade.C
            )
        ).toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues.first()::class.java
        )

        Assert.assertEquals(
            Resource.Failure::class.java,
            flowValues[1]::class.java
        )
    }

    @Test
    fun `If request is successful, then emits exactly two values`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = true

        Assert.assertEquals(
            2,
            submitQuestionGrades.invoke(
                mapOf(
                    ExamQuestion(12, 1, "Questions 1", "Answer 1") to Grade.A,
                    ExamQuestion(32, 2, "Questions 2", "Answer 2") to Grade.B,
                    ExamQuestion(27, 3, "Questions 3", "Answer 3") to Grade.C
                )
            ).count()
        )
    }

    @Test
    fun `If request is unsuccessful, then emits exactly two values`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = false

        Assert.assertEquals(
            2,
            submitQuestionGrades.invoke(
                mapOf(
                    ExamQuestion(12, 1, "Questions 1", "Answer 1") to Grade.A,
                    ExamQuestion(32, 2, "Questions 2", "Answer 2") to Grade.B,
                    ExamQuestion(27, 3, "Questions 3", "Answer 3") to Grade.C
                )
            ).count()
        )
    }
}