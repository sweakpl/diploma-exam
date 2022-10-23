package com.sweak.diplomaexam.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun Dialog(
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    onlyPositiveButton: Boolean = false,
    onPositiveClick: () -> Unit,
    positiveButtonText: String,
    onNegativeClick: (() -> Unit)? = null,
    negativeButtonText: String? = null
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colors.surface)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(
                    MaterialTheme.space.medium,
                    MaterialTheme.space.medium,
                    MaterialTheme.space.medium,
                    MaterialTheme.space.small
                )
            )
            Text(
                text = message,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(
                    MaterialTheme.space.medium,
                    MaterialTheme.space.small,
                    MaterialTheme.space.medium,
                    MaterialTheme.space.small
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (!onlyPositiveButton) {
                    Button(
                        onClick = onNegativeClick ?: onDismissRequest,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        ),
                        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                MaterialTheme.space.medium,
                                MaterialTheme.space.small,
                                MaterialTheme.space.small,
                                MaterialTheme.space.medium
                            )
                    ) {
                        Text(
                            text = negativeButtonText ?: "",
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier.padding(MaterialTheme.space.extraSmall)
                        )
                    }
                }
                Button(
                    onClick = onPositiveClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            if (!onlyPositiveButton) {
                                MaterialTheme.space.small
                            } else {
                                MaterialTheme.space.medium
                            },
                            MaterialTheme.space.small,
                            MaterialTheme.space.medium,
                            MaterialTheme.space.medium
                        )
                ) {
                    Text(
                        text = positiveButtonText,
                        modifier = Modifier.padding(MaterialTheme.space.extraSmall)
                    )
                }
            }
        }
    }
}