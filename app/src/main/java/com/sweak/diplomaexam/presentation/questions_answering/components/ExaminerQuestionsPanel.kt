package com.sweak.diplomaexam.presentation.questions_answering.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.ExamQuestion
import com.sweak.diplomaexam.presentation.components.LoadingLayout
import com.sweak.diplomaexam.presentation.components.LoadingLayoutSize
import com.sweak.diplomaexam.presentation.ui.theme.space
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun ExaminerQuestionsPanel(
    questions: List<ExamQuestion>,
    displayMode: ExaminerQuestionsPanelDisplayMode,
    isLoadingResponse: Boolean,
    isWaitingForStudentReadiness: Boolean,
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
        if (displayMode == ExaminerQuestionsPanelDisplayMode.COMPACT) {
            CompactExaminerQuestionsPager(
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

            AnimatedContent(
                targetState = isLoadingResponse,
                modifier = modifier.fillMaxWidth()
            ) { targetState ->
                if (targetState) {
                    LoadingLayout(loadingLayoutSize = LoadingLayoutSize.SMALL)
                } else {
                    AnimatedVisibility(visible = isWaitingForStudentReadiness) {
                        LoadingLayout(
                            loadingLayoutSize = LoadingLayoutSize.SMALL,
                            text = stringResource(R.string.waiting_for_readiness)
                        )
                    }
                }
            }
        } else if (displayMode == ExaminerQuestionsPanelDisplayMode.MEDIUM_OR_EXPANDED) {
            MediumOrExpandedExaminerQuestionsPager(
                questions = questions,
                pagerState = pagerState,
                isLoadingResponse = isLoadingResponse,
                isWaitingForStudentReadiness = isWaitingForStudentReadiness,
                modifier = Modifier.weight(1f)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.space.medium)
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
        }
    }
}

enum class ExaminerQuestionsPanelDisplayMode {
    COMPACT, MEDIUM_OR_EXPANDED
}

@ExperimentalPagerApi
@Composable
fun CompactExaminerQuestionsPager(
    questions: List<ExamQuestion>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val composableScope = rememberCoroutineScope()

    val questionsAnswersExpandedStates = remember {
        SnapshotStateList<Boolean>().apply { addAll(questions.map { false }) }
    }

    HorizontalPager(
        count = questions.size,
        state = pagerState,
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) { page ->
        val currentQuestion = questions[page]

        val rotation = remember { Animatable(0f) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.space.extraSmall)
                .verticalScroll(rememberScrollState())
        ) {
            Column {
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
                            style = MaterialTheme.typography.h2,
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

                if (currentQuestion.answer != null) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(
                                if (questionsAnswersExpandedStates[page])
                                    R.string.hide_answer
                                else
                                    R.string.show_answer
                            )
                        )

                        IconButton(
                            onClick = {
                                if (!rotation.isRunning) {
                                    composableScope.launch {
                                        rotation.animateTo(
                                            targetValue =
                                            if (questionsAnswersExpandedStates[page]) 0f else 180f
                                        )
                                        questionsAnswersExpandedStates[page] =
                                            !questionsAnswersExpandedStates[page]
                                    }
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_expand_circle),
                                contentDescription = "Show the answer button",
                                tint = MaterialTheme.colors.onPrimary,
                                modifier = Modifier
                                    .size(size = 24.dp)
                                    .padding(start = MaterialTheme.space.extraSmall)
                                    .rotate(rotation.value)
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = questionsAnswersExpandedStates[page],
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = currentQuestion.answer)
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun MediumOrExpandedExaminerQuestionsPager(
    questions: List<ExamQuestion>,
    pagerState: PagerState,
    isLoadingResponse: Boolean,
    isWaitingForStudentReadiness: Boolean,
    modifier: Modifier = Modifier
) {
    val composableScope = rememberCoroutineScope()

    val questionsAnswersExpandedStates = remember {
        SnapshotStateList<Boolean>().apply { addAll(questions.map { false }) }
    }

    HorizontalPager(
        count = questions.size,
        state = pagerState,
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) { page ->
        val currentQuestion = questions[page]

        val rotation = remember { Animatable(0f) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.space.extraSmall)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = MaterialTheme.space.large)
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
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
                            style = MaterialTheme.typography.h2,
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

                if (currentQuestion.answer != null) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(
                                if (questionsAnswersExpandedStates[page])
                                    R.string.hide_answer
                                else
                                    R.string.show_answer
                            )
                        )

                        IconButton(
                            onClick = {
                                if (!rotation.isRunning) {
                                    composableScope.launch {
                                        rotation.animateTo(
                                            targetValue =
                                            if (questionsAnswersExpandedStates[page]) 180f else 0f
                                        )
                                        questionsAnswersExpandedStates[page] =
                                            !questionsAnswersExpandedStates[page]
                                    }
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_expand_circle),
                                contentDescription = "Show the answer button",
                                tint = MaterialTheme.colors.onPrimary,
                                modifier = Modifier
                                    .size(size = 24.dp)
                                    .padding(start = MaterialTheme.space.extraSmall)
                                    .rotate(rotation.value)
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = questionsAnswersExpandedStates[page],
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = currentQuestion.answer)
                    }
                }
            }

            AnimatedContent(
                targetState = isLoadingResponse,
                modifier = modifier
                    .padding(start = MaterialTheme.space.large)
                    .weight(1f)
            ) { targetState ->
                if (targetState) {
                    LoadingLayout(loadingLayoutSize = LoadingLayoutSize.SMALL)
                } else {
                    AnimatedVisibility(visible = isWaitingForStudentReadiness) {
                        LoadingLayout(
                            loadingLayoutSize = LoadingLayoutSize.SMALL,
                            text = stringResource(R.string.waiting_for_readiness)
                        )
                    }
                }
            }
        }
    }
}