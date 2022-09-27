package com.sweak.diplomaexam.presentation.questions_draw.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.UserRole
import com.sweak.diplomaexam.presentation.components.LoadingLayout
import com.sweak.diplomaexam.presentation.components.LoadingLayoutSize
import com.sweak.diplomaexam.presentation.components.ThickWhiteButton
import com.sweak.diplomaexam.presentation.ui.theme.space

@ExperimentalAnimationApi
@Composable
fun DrawnQuestionsOperations(
    userRole: UserRole,
    isLoadingResponse: Boolean,
    hasStudentRequestedRedraw: Boolean,
    waitingForDecisionFrom: UserRole,
    onAcceptQuestions: () -> Unit,
    onRedrawQuestions: () -> Unit,
    onAllowQuestionsRedraw: () -> Unit,
    onDisallowQuestionsRedraw: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = isLoadingResponse,
        modifier = modifier
    ) { targetState1 ->
        if (targetState1) {
            LoadingLayout(
                loadingLayoutSize = LoadingLayoutSize.SMALL,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            AnimatedContent(
                targetState = !hasStudentRequestedRedraw &&
                        waitingForDecisionFrom == UserRole.USER_STUDENT
            ) { targetState2 ->
                if (targetState2) {
                    if (userRole == UserRole.USER_STUDENT) {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            ThickWhiteButton(
                                text = stringResource(R.string.accept),
                                onClick = onAcceptQuestions,
                                modifier = Modifier.padding(bottom = MaterialTheme.space.medium)
                            )

                            ThickWhiteButton(
                                text = stringResource(R.string.draw_again),
                                onClick = onRedrawQuestions,
                                isTransparent = true
                            )
                        }
                    } else {
                        LoadingLayout(
                            loadingLayoutSize = LoadingLayoutSize.SMALL,
                            text = stringResource(R.string.waiting_for_readiness),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                } else {
                    AnimatedContent(
                        targetState = waitingForDecisionFrom == UserRole.USER_EXAMINER
                    ) { targetState3 ->
                        if (targetState3) {
                            if (userRole == UserRole.USER_STUDENT) {
                                LoadingLayout(
                                    loadingLayoutSize = LoadingLayoutSize.SMALL,
                                    text = stringResource(R.string.waiting_for_permission),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } else {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = stringResource(R.string.student_asked_for_redraw),
                                        style = MaterialTheme.typography.h2,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(bottom = MaterialTheme.space.medium)
                                    )

                                    ThickWhiteButton(
                                        text = stringResource(R.string.allow),
                                        onClick = onAllowQuestionsRedraw,
                                        modifier = Modifier
                                            .padding(bottom = MaterialTheme.space.medium)
                                    )

                                    ThickWhiteButton(
                                        text = stringResource(R.string.dont_allow),
                                        onClick = onDisallowQuestionsRedraw,
                                        isTransparent = true
                                    )
                                }
                            }
                        } else {
                            if (userRole == UserRole.USER_STUDENT) {
                                ThickWhiteButton(
                                    text = stringResource(R.string.accept),
                                    onClick = onAcceptQuestions,
                                    modifier = Modifier
                                        .padding(bottom = MaterialTheme.space.medium)
                                )
                            } else {
                                LoadingLayout(
                                    loadingLayoutSize = LoadingLayoutSize.SMALL,
                                    text = stringResource(R.string.waiting_for_readiness),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}