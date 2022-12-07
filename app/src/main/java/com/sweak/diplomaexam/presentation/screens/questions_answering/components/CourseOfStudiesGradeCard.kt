package com.sweak.diplomaexam.presentation.screens.questions_answering.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.presentation.ui.theme.space

@ExperimentalComposeUiApi
@Composable
fun CourseOfStudiesGradeCard(
    gradeString: String?,
    onGradeStringChanged: (String) -> Unit,
    canSelectGrade: Boolean,
    modifier: Modifier = Modifier
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Card(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MaterialTheme.space.medium)
        ) {
            Text(
                text = stringResource(R.string.course_of_studies_grade),
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = gradeString ?: "",
                onValueChange = { onGradeStringChanged(it) },
                enabled = canSelectGrade,
                label = {
                    Text(
                        text = stringResource(R.string.grade),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colors.onSurface,
                    cursorColor = MaterialTheme.colors.onSurface,
                    trailingIconColor = MaterialTheme.colors.onSurface,
                    focusedBorderColor = MaterialTheme.colors.onSurface,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface,
                    backgroundColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onSurface
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        softwareKeyboardController?.hide()
                    }
                ),
                trailingIcon = {
                    IconButton(
                        onClick = { onGradeStringChanged("") },
                        enabled = canSelectGrade
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear text"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.space.medium)
            )
        }
    }
}