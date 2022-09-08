package com.sweak.diplomaexam.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun Header(
    displayMode: HeaderDisplayMode,
    modifier: Modifier = Modifier,
    proceedButtonEnabled: Boolean = true
) {
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
                    onClick = {},
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
                onClick = {},
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