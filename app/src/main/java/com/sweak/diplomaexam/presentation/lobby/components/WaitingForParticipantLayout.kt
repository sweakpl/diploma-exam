package com.sweak.diplomaexam.presentation.lobby.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.common.UserRole
import com.sweak.diplomaexam.presentation.components.LoadingLayout
import com.sweak.diplomaexam.presentation.components.ThickWhiteButton
import com.sweak.diplomaexam.presentation.ui.theme.space

@ExperimentalAnimationApi
@Composable
fun WaitingForParticipantLayout(
    userRole: UserRole?,
    hasOtherUserJoined: Boolean,
    isSessionInStartingProcess: Boolean,
    startExamSession: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = userRole != null,
        modifier = modifier
    ) { targetState ->
        if (targetState) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(
                        if (!hasOtherUserJoined) {
                            when (userRole) {
                                UserRole.USER_EXAMINER -> R.string.waiting_for_student
                                UserRole.USER_STUDENT -> R.string.waiting_for_examiner
                                null -> R.string.waiting
                            }
                        } else {
                            when (userRole) {
                                UserRole.USER_EXAMINER -> R.string.student_joined_session
                                UserRole.USER_STUDENT -> R.string.waiting_for_examiner_to_start_session
                                else -> R.string.waiting
                            }
                        }
                    ),
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = MaterialTheme.space.large)
                )

                AnimatedContent(
                    targetState = userRole == UserRole.USER_EXAMINER &&
                            hasOtherUserJoined &&
                            !isSessionInStartingProcess
                ) { state ->
                    if (state) {
                        ThickWhiteButton(
                            text = stringResource(R.string.start_exam),
                            onClick = startExamSession
                        )
                    } else {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.size(96.dp)
                        )
                    }
                }
            }
        } else {
            LoadingLayout()
        }
    }
}