package com.sweak.diplomaexam.presentation.components

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.User
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun Header(
    displayMode: HeaderDisplayMode,
    usersInSession: List<User>,
    modifier: Modifier = Modifier,
    proceedButtonEnabled: Boolean = true
) {
    var isUsersListPopupExpanded by remember { mutableStateOf(false) }

    val popupExpandedStates = remember { MutableTransitionState(isUsersListPopupExpanded) }
    popupExpandedStates.targetState = isUsersListPopupExpanded

    if (popupExpandedStates.currentState || popupExpandedStates.targetState) {
        val offset = LocalDensity.current.run { 50.dp.toPx().toInt() }

        Popup(
            offset = IntOffset(x = offset, y = offset),
            onDismissRequest = { isUsersListPopupExpanded = false }
        ) {
            val transition = updateTransition(popupExpandedStates, "Popup")

            val alpha by transition.animateFloat(
                transitionSpec = { tween(durationMillis = 300) },
                label = "PopupTransition"
            ) {
                if (it) 1f else 0f
            }

            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .graphicsLayer { this.alpha = alpha }
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    contentPadding = PaddingValues(MaterialTheme.space.medium)
                ) {
                    item {
                        Text(
                            text = stringResource(R.string.users_in_session),
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Start
                        )
                    }

                    if (usersInSession.isEmpty()) {
                        item {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.onSurface,
                                modifier = Modifier
                                    .size(MaterialTheme.space.large)
                                    .padding(vertical = MaterialTheme.space.medium)
                            )

                            Spacer(modifier = Modifier.height(MaterialTheme.space.medium))
                        }
                    } else {
                        items(usersInSession) { user ->
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(top = MaterialTheme.space.medium)
                                    .wrapContentWidth()
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_person),
                                    contentDescription = "User in session icon",
                                    tint = MaterialTheme.colors.onSurface,
                                    modifier = Modifier.padding(end = MaterialTheme.space.small)
                                )

                                Text(
                                    text = user.email ?: "---",
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.onSurface,
                                    textAlign = TextAlign.Start,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (displayMode == HeaderDisplayMode.COMPACT) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.space.large)
            ) {
                IconButton(
                    onClick = { isUsersListPopupExpanded = true },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_group),
                        contentDescription = "Session participants button",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier.size(36.dp).alpha(if (proceedButtonEnabled) 1f else 0f),
                    enabled = proceedButtonEnabled
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_done),
                        contentDescription = "Proceed with the session button",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }

            Text(
                text = stringResource(R.string.drawing_questions),
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center
            )
        }
    } else if (displayMode == HeaderDisplayMode.MEDIUM_OR_EXPANDED) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { isUsersListPopupExpanded = true },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_group),
                    contentDescription = "Session participants button",
                    tint = MaterialTheme.colors.onPrimary
                )
            }

            Text(
                text = stringResource(R.string.drawing_questions),
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = {},
                modifier = Modifier.size(36.dp).alpha(if (proceedButtonEnabled) 1f else 0f),
                enabled = proceedButtonEnabled
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_done),
                    contentDescription = "Proceed with the session button",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

enum class HeaderDisplayMode {
    COMPACT, MEDIUM_OR_EXPANDED
}