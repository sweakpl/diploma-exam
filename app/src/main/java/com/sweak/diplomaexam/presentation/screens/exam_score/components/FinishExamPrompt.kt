package com.sweak.diplomaexam.presentation.screens.exam_score.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.presentation.screens.common.components.ThickWhiteButton
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun FinishExamPrompt(
    onFinishExamClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.thank_you_for_participation),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = MaterialTheme.space.medium)
        )

        ThickWhiteButton(
            text = stringResource(R.string.finish_exam),
            onClick = onFinishExamClick
        )
    }
}