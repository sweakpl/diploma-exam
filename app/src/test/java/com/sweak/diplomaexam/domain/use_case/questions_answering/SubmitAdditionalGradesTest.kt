package com.sweak.diplomaexam.domain.use_case.questions_answering

import com.sweak.diplomaexam.data.repository.QuestionsAnsweringRepositoryFake
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
class SubmitAdditionalGradesTest {

    private lateinit var submitAdditionalGrades: SubmitAdditionalGrades
    private lateinit var questionsAnsweringRepository: QuestionsAnsweringRepositoryFake

    @Before
    fun setUp() {
        questionsAnsweringRepository = QuestionsAnsweringRepositoryFake()
        submitAdditionalGrades = SubmitAdditionalGrades(questionsAnsweringRepository)
    }

    @Test
    fun `If all grades are valid, request is successful, then first value is Loading, second is Success`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = true

        val flowValues = submitAdditionalGrades.invoke(Grade.A, Grade.B, "4.659").toList()

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
    fun `If all grades are valid, request is unsuccessful, then first value is Loading, second is Failure`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = false

        val flowValues = submitAdditionalGrades.invoke(Grade.A, Grade.B, "4.659").toList()

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
    fun `If all grades are invalid, then first value is Loading, second is Failure`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = false

        val flowValues = submitAdditionalGrades.invoke(null, null, null).toList()

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
    fun `If thesisPresentationGrade grade is invalid, then first value is Loading, second is Failure`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = false

        val flowValues = submitAdditionalGrades.invoke(null, Grade.B, "4.659").toList()

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
    fun `If thesisGrade grade is invalid, then first value is Loading, second is Failure`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = false

        val flowValues = submitAdditionalGrades.invoke(Grade.A, null, "4.659").toList()

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
    fun `If courseOfStudiesPreciseGradeString grade is invalid, then first value is Loading, second is Failure`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = false

        val flowValues = submitAdditionalGrades.invoke(Grade.A, Grade.B, null).toList()

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
    fun `If all grades are valid, request is successful, then emits exactly two values`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = true

        Assert.assertEquals(
            2,
            submitAdditionalGrades.invoke(Grade.A, Grade.B, "4.659").count()
        )
    }

    @Test
    fun `If all grades are valid, request is unsuccessful, then emits exactly two values`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = false

        Assert.assertEquals(
            2,
            submitAdditionalGrades.invoke(Grade.A, Grade.B, "4.659").count()
        )
    }
}