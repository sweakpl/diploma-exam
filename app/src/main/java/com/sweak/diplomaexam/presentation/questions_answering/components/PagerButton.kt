package com.sweak.diplomaexam.presentation.questions_answering.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun PagerButton(
    onClick: () -> Unit,
    direction: PagerButtonDirection,
    enabled: Boolean,
    size: Dp
) {
    IconButton(
        onClick = onClick,
        enabled = enabled
    ) {
        Icon(
            imageVector = if (direction == PagerButtonDirection.RIGHT) Icons.Default.ArrowForwardIos
            else Icons.Default.ArrowBackIos,
            contentDescription = "Questions page change " +
                    "${if (direction == PagerButtonDirection.RIGHT) "right" else "left"} arrow",
            tint =
            if (enabled) MaterialTheme.colors.onPrimary
            else MaterialTheme.colors.primary,
            modifier = Modifier.size(size = size)
        )
    }
}

enum class PagerButtonDirection {
    LEFT, RIGHT
}