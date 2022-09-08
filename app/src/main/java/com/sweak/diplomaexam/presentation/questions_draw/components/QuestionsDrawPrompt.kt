package com.sweak.diplomaexam.presentation.questions_draw.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.presentation.components.ThickWhiteButton
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun QuestionsDrawPrompt(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.please_draw_questions),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = MaterialTheme.space.large)
        )

        ThickWhiteButton(
            text = stringResource(R.string.draw),
            onClick = {}
        )
    }
}