package com.sweak.diplomaexam.domain.use_case.exam_score

import com.sweak.diplomaexam.data.repository.ExamScoreRepositoryFake
import com.sweak.diplomaexam.domain.model.common.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetExamScoreStateTest {

    private lateinit var getExamScoreState: GetExamScoreState
    private lateinit var examScoreRepository: ExamScoreRepositoryFake

    @Before
    fun setUp() {
        examScoreRepository = ExamScoreRepositoryFake()
        getExamScoreState = GetExamScoreState(examScoreRepository)
    }

    @Test
    fun `If request is successful, then first value is Loading, second is Success`() = runTest {
        examScoreRepository.isSuccessfulResponse = true

        val flowValues = getExamScoreState.invoke().toList()

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
        examScoreRepository.isSuccessfulResponse = false

        val authenticationFlowValues = getExamScoreState.invoke().toList()

        Assert.assertEquals(
            Resource.Loading::class.java,
            authenticationFlowValues.first()::class.java
        )

        Assert.assertEquals(
            Resource.Failure::class.java,
            authenticationFlowValues[1]::class.java
        )
    }

    @Test
    fun `If request is successful, then emits exactly two values`() = runTest {
        examScoreRepository.isSuccessfulResponse = true

        Assert.assertEquals(
            2,
            getExamScoreState.invoke().count()
        )
    }

    @Test
    fun `If request is unsuccessful, then emits exactly two values`() = runTest {
        examScoreRepository.isSuccessfulResponse = false

        Assert.assertEquals(
            2,
            getExamScoreState.invoke().count()
        )
    }
}