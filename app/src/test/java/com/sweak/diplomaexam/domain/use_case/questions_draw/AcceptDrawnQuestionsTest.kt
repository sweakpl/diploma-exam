package com.sweak.diplomaexam.domain.use_case.questions_draw

import com.sweak.diplomaexam.data.repository.QuestionsDrawRepositoryFake
import com.sweak.diplomaexam.domain.model.common.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AcceptDrawnQuestionsTest {

    private lateinit var acceptDrawnQuestions: AcceptDrawnQuestions
    private lateinit var questionsDrawRepository: QuestionsDrawRepositoryFake

    @Before
    fun setUp() {
        questionsDrawRepository = QuestionsDrawRepositoryFake()
        acceptDrawnQuestions = AcceptDrawnQuestions(questionsDrawRepository)
    }

    @Test
    fun `If request is successful, then first value is Loading, second is Success`() = runTest {
        questionsDrawRepository.isSuccessfulResponse = true

        val flowValues = acceptDrawnQuestions.invoke().toList()

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
        questionsDrawRepository.isSuccessfulResponse = false

        val flowValues = acceptDrawnQuestions.invoke().toList()

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
        questionsDrawRepository.isSuccessfulResponse = true

        Assert.assertEquals(
            2,
            acceptDrawnQuestions.invoke().count()
        )
    }

    @Test
    fun `If request is unsuccessful, then emits exactly two values`() = runTest {
        questionsDrawRepository.isSuccessfulResponse = false

        Assert.assertEquals(
            2,
            acceptDrawnQuestions.invoke().count()
        )
    }
}