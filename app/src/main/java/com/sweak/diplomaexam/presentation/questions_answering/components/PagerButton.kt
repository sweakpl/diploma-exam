package com.sweak.diplomaexam.presentation.questions_answering.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.sweak.diplomaexam.R

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
            painter = painterResource(
                if (direction == PagerButtonDirection.RIGHT) R.drawable.ic_arrow_forward_ios
                else R.drawable.ic_arrow_back_ios
            ),
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