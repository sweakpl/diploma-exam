package com.sweak.diplomaexam.domain.use_case.questions_draw

import com.sweak.diplomaexam.data.repository.QuestionsDrawRepositoryFake
import com.sweak.diplomaexam.domain.model.common.Resource
import com.sweak.diplomaexam.domain.model.common.UserRole
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetQuestionsDrawStateTest {

    private lateinit var getQuestionsDrawState: GetQuestionsDrawState
    private lateinit var questionsDrawRepository: QuestionsDrawRepositoryFake

    @Before
    fun setUp() {
        questionsDrawRepository = QuestionsDrawRepositoryFake()
        getQuestionsDrawState = GetQuestionsDrawState(questionsDrawRepository)
    }

    @Test
    fun `If user is examiner, requests are successful and user will have to make a decision, then first value is Loading, all the others are Success and flow terminates by itself eventually`() = runTest {
        questionsDrawRepository.isSuccessfulResponse = true
        questionsDrawRepository.userRole = UserRole.USER_EXAMINER
        questionsDrawRepository.willBeWaitingForCurrentUser = true

        val flowValues = getQuestionsDrawState.invoke().toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues.first()::class.java
        )

        flowValues.subList(1, flowValues.size).forEach {
            Assert.assertEquals(
                Resource.Success::class.java,
                it::class.java
            )
        }
    }

    @Test
    fun `If user is examiner, requests are successful and user will not have to make a decision, then first value is Loading, all the others are Success and flow is not terminated by itself until at least 50 emissions`() = runTest {
        questionsDrawRepository.isSuccessfulResponse = true
        questionsDrawRepository.userRole = UserRole.USER_EXAMINER
        questionsDrawRepository.willBeWaitingForCurrentUser = false

        val flowValues = getQuestionsDrawState.invoke().take(50).toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues.first()::class.java
        )

        flowValues.subList(1, flowValues.size).forEach {
            Assert.assertEquals(
                Resource.Success::class.java,
                it::class.java
            )
        }
    }

    @Test
    fun `If user is student, requests are successful and user will have to make a decision, then first value is Loading, all the others are Success and flow terminates by itself eventually`() = runTest {
        questionsDrawRepository.isSuccessfulResponse = true
        questionsDrawRepository.userRole = UserRole.USER_STUDENT
        questionsDrawRepository.willBeWaitingForCurrentUser = true

        val flowValues = getQuestionsDrawState.invoke().toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues.first()::class.java
        )

        flowValues.subList(1, flowValues.size).forEach {
            Assert.assertEquals(
                Resource.Success::class.java,
                it::class.java
            )
        }
    }

    @Test
    fun `If user is student, requests are successful and user will not have to make a decision, then first value is Loading, all the others are Success and flow is not terminated by itself until at least 50 emissions`() = runTest {
        questionsDrawRepository.isSuccessfulResponse = true
        questionsDrawRepository.userRole = UserRole.USER_STUDENT
        questionsDrawRepository.willBeWaitingForCurrentUser = false

        val flowValues = getQuestionsDrawState.invoke().take(50).toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues.first()::class.java
        )

        flowValues.subList(1, flowValues.size).forEach {
            Assert.assertEquals(
                Resource.Success::class.java,
                it::class.java
            )
        }
    }

    @Test
    fun `If some request is unsuccessful, then first value is Loading, and last one is Failure`() = runTest {
        questionsDrawRepository.isSuccessfulResponse = false
        questionsDrawRepository.userRole = UserRole.USER_EXAMINER

        val flowValues = getQuestionsDrawState.invoke().toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            flowValues.first()::class.java
        )

        Assert.assertEquals(
            Resource.Failure::class.java,
            flowValues.last()::class.java
        )
    }
}