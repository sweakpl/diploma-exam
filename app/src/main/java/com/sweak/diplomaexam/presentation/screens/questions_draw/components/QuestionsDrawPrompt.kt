package com.sweak.diplomaexam.presentation.screens.questions_draw.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.presentation.screens.common.components.ThickWhiteButton
import com.sweak.diplomaexam.presentation.ui.theme.space

@ExperimentalAnimationApi
@Composable
fun QuestionsDrawPrompt(
    onDrawClick: () -> Unit,
    areQuestionsInDrawingProcess: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.please_draw_questions),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = MaterialTheme.space.large)
        )

        AnimatedContent(targetState = !areQuestionsInDrawingProcess) { state ->
            if (state) {
                ThickWhiteButton(
                    text = stringResource(R.string.draw),
                    onClick = onDrawClick
                )
            } else {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.size(96.dp)
                )
            }
        }
    }
}