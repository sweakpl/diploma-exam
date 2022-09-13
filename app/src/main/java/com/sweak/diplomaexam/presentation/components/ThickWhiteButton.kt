package com.sweak.diplomaexam.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun ThickWhiteButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isTransparent: Boolean = false
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor =
            if (isTransparent) Color.Companion.Transparent else MaterialTheme.colors.surface
        ),
        elevation = ButtonDefaults
            .elevation(
                defaultElevation = if (isTransparent) 0.dp else MaterialTheme.space.extraSmall
            ),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color =
            if (isTransparent) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary
        )
    }
}