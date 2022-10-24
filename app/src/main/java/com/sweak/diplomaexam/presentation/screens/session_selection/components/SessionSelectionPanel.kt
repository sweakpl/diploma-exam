package com.sweak.diplomaexam.presentation.screens.session_selection.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.sweak.diplomaexam.domain.model.session_selection.AvailableSession
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun SessionSelectionPanel(
    availableSessions: List<AvailableSession>,
    onSessionSelected: (AvailableSession) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        items(availableSessions) { availableSession ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.space.small)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = availableSession.studentEmail,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = MaterialTheme.space.medium)
                    )

                    IconButton(onClick = { onSessionSelected(availableSession) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Select available session button",
                            tint = MaterialTheme.colors.onSurface,
                            modifier = Modifier.size(size = MaterialTheme.space.medium)
                        )
                    }
                }
            }
        }
    }
}