package com.sweak.diplomaexam.presentation.questions_answering.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.ExamQuestion
import com.sweak.diplomaexam.presentation.components.LoadingLayout
import com.sweak.diplomaexam.presentation.components.LoadingLayoutSize
import com.sweak.diplomaexam.presentation.components.ThickWhiteButton
import com.sweak.diplomaexam.presentation.ui.theme.space
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun StudentQuestionsPanel(
    questions: List<ExamQuestion>,
    displayMode: StudentQuestionsPanelDisplayMode,
    isLoadingResponse: Boolean,
    isWaitingForStudentReadiness: Boolean,
    onConfirmReadiness: () -> Unit,
    modifier: Modifier = Modifier
) {
    val composableScope = rememberCoroutineScope()

    var isLeftArrowEnabled by rememberSaveable { mutableStateOf(false) }
    var isRightArrowEnabled by rememberSaveable { mutableStateOf(true) }
    val pagerState = rememberPagerState()

    val onRightButtonClick: () -> Unit = {
        composableScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage + 1,
                0f
            )
        }
    }
    val onLeftButtonClick: () -> Unit = {
        composableScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage - 1,
                0f
            )
        }
    }

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            when (page) {
                0 -> isLeftArrowEnabled = false
                2 -> isRightArrowEnabled = false
                else -> {
                    isLeftArrowEnabled = true
                    isRightArrowEnabled = true
                }
            }
        }
    }

    Column(modifier = modifier) {
        if (displayMode == StudentQuestionsPanelDisplayMode.COMPACT) {
            StudentQuestionsPager(
                questions = questions,
                pagerState = pagerState,
                modifier = Modifier.weight(1f)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.space.large)
            ) {
                PagerButton(
                    onClick = onLeftButtonClick,
                    direction = PagerButtonDirection.LEFT,
                    enabled = isLeftArrowEnabled,
                    size = MaterialTheme.space.large
                )

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = MaterialTheme.colors.onPrimary,
                    inactiveColor = MaterialTheme.colors.primary
                )

                PagerButton(
                    onClick = onRightButtonClick,
                    direction = PagerButtonDirection.RIGHT,
                    enabled = isRightArrowEnabled,
                    size = MaterialTheme.space.large
                )
            }

            StudentQuestionsOperations(
                isLoadingResponse = isLoadingResponse,
                isWaitingForStudentReadiness = isWaitingForStudentReadiness,
                onConfirmReadiness = onConfirmReadiness
            )
        } else if (displayMode == StudentQuestionsPanelDisplayMode.MEDIUM_OR_EXPANDED) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                PagerButton(
                    onClick = onLeftButtonClick,
                    direction = PagerButtonDirection.LEFT,
                    enabled = isLeftArrowEnabled,
                    size = 48.dp
                )

                StudentQuestionsPager(
                    questions = questions,
                    pagerState = pagerState,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(horizontal = MaterialTheme.space.large)
                )

                PagerButton(
                    onClick = onRightButtonClick,
                    direction = PagerButtonDirection.RIGHT,
                    enabled = isRightArrowEnabled,
                    size = 48.dp
                )
            }

            StudentQuestionsOperations(
                isLoadingResponse = isLoadingResponse,
                isWaitingForStudentReadiness = isWaitingForStudentReadiness,
                onConfirmReadiness = onConfirmReadiness,
                modifier = Modifier.padding(horizontal = 80.dp)
            )
        }
    }
}

enum class StudentQuestionsPanelDisplayMode {
    COMPACT, MEDIUM_OR_EXPANDED
}

@ExperimentalPagerApi
@Composable
fun StudentQuestionsPager(
    questions: List<ExamQuestion>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        count = questions.size,
        state = pagerState,
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) { page ->
        val currentQuestion = questions[page]

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.space.extraSmall)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(
                            R.string.question_with_number,
                            currentQuestion.number
                        ),
                        style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(
                            bottom = MaterialTheme.space.small,
                            top = MaterialTheme.space.medium,
                            start = MaterialTheme.space.medium,
                            end = MaterialTheme.space.medium
                        )
                    )

                    Text(
                        text = currentQuestion.question,
                        style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Normal),
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(
                            bottom = MaterialTheme.space.medium,
                            top = MaterialTheme.space.small,
                            start = MaterialTheme.space.medium,
                            end = MaterialTheme.space.small
                        )
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun StudentQuestionsOperations(
    isLoadingResponse: Boolean,
    isWaitingForStudentReadiness: Boolean,
    onConfirmReadiness: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = isLoadingResponse,
        modifier = modifier.fillMaxWidth()
    ) { targetState ->
        if (targetState) {
            LoadingLayout(loadingLayoutSize = LoadingLayoutSize.SMALL)
        } else {
            AnimatedContent(targetState = isWaitingForStudentReadiness) { state ->
                if (state) {
                    ThickWhiteButton(
                        text = stringResource(R.string.confirm_readiness),
                        onClick = onConfirmReadiness
                    )
                } else {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_info),
                            contentDescription = "Information icon",
                            tint = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.size(size = MaterialTheme.space.medium)
                        )

                        Text(
                            text = stringResource(R.string.after_answer_confirm_readiness),
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(start = MaterialTheme.space.medium)
                        )
                    }
                }
            }
        }
    }
}