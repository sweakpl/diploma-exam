package com.sweak.diplomaexam.presentation.screens.questions_draw.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.common.ExamQuestion
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun DrawnQuestionsList(
    questions: List<ExamQuestion>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        items(questions) { question ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.space.small)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(R.string.question_with_number, question.number),
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(
                            bottom = MaterialTheme.space.small,
                            top = MaterialTheme.space.medium,
                            start = MaterialTheme.space.medium,
                            end = MaterialTheme.space.medium
                        )
                    )

                    Text(
                        text = question.question,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(
                            bottom = MaterialTheme.space.medium,
                            top = MaterialTheme.space.small,
                            start = MaterialTheme.space.medium,
                            end = MaterialTheme.space.small
                        )
                    )
                }
            }
        }
    }
}