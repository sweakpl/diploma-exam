package com.sweak.diplomaexam.domain.use_case.questions_answering

import com.sweak.diplomaexam.data.repository.QuestionsAnsweringRepositoryFake
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
class GetQuestionsAnsweringStateTest {

    private lateinit var getQuestionsAnsweringState: GetQuestionsAnsweringState
    private lateinit var questionsAnsweringRepository: QuestionsAnsweringRepositoryFake

    @Before
    fun setUp() {
        questionsAnsweringRepository = QuestionsAnsweringRepositoryFake()
        getQuestionsAnsweringState = GetQuestionsAnsweringState(questionsAnsweringRepository)
    }

    @Test
    fun `If user is examiner, requests are successful and user will have to make a decision, then first value is Loading, all the others are Success and flow terminates by itself eventually`() = runTest {
        questionsAnsweringRepository.isSuccessfulResponse = true
        questionsAnsweringRepository.userRole = UserRole.USER_EXAMINER
        questionsAnsweringRepository.willBeWaitingForStudentUser = false
        questionsAnsweringRepository.willBeWaitingForFinalEvaluation = true

        val flowValues = getQuestionsAnsweringState.invoke().toList()

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
        questionsAnsweringRepository.userRole = UserRole.USER_EXAMINER
        questionsAnsweringRepository.willBeWaitingForStudentUser = true
        questionsAnsweringRepository.willBeWaitingForFinalEvaluation = false

        val flowValues = getQuestionsAnsweringState.invoke().take(50).toList()

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
        questionsAnsweringRepository.isSuccessfulResponse = true
        questionsAnsweringRepository.userRole = UserRole.USER_STUDENT
        questionsAnsweringRepository.willBeWaitingForStudentUser = true
        questionsAnsweringRepository.willBeWaitingForFinalEvaluation = false

        val flowValues = getQuestionsAnsweringState.invoke().toList()

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
        questionsAnsweringRepository.isSuccessfulResponse = true
        questionsAnsweringRepository.userRole = UserRole.USER_STUDENT
        questionsAnsweringRepository.willBeWaitingForStudentUser = false
        questionsAnsweringRepository.willBeWaitingForFinalEvaluation = true

        val flowValues = getQuestionsAnsweringState.invoke().take(50).toList()

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
        questionsAnsweringRepository.isSuccessfulResponse = false
        questionsAnsweringRepository.userRole = UserRole.USER_EXAMINER

        val flowValues = getQuestionsAnsweringState.invoke().toList()

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