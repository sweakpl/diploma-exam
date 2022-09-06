package com.sweak.diplomaexam.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun ThickWhiteButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        elevation = ButtonDefaults.elevation(defaultElevation = MaterialTheme.space.extraSmall),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.primary
        )
    }
}