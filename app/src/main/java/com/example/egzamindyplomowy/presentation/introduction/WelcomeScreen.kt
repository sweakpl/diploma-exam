package com.example.egzamindyplomowy.presentation.introduction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.egzamindyplomowy.R
import com.example.egzamindyplomowy.presentation.ui.theme.space

@Composable
fun WelcomeScreen() {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primaryVariant
                    )
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.space.large)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_application_icon),
                contentDescription = "Application icon - university graduation cap",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .size(96.dp)
                    .padding(bottom = MaterialTheme.space.medium)
            )

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = MaterialTheme.space.extraLarge)
            )

            Text(
                text = stringResource(R.string.welcome_to_exam),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = MaterialTheme.space.medium)
            )

            Text(
                text = stringResource(R.string.tell_me_who_you_are),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = MaterialTheme.space.extraLarge)
            )

            ExamRoleChoiceButton(
                text = stringResource(R.string.student_instrumental),
                onClick = {/*TODO*/ }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.space.large))

            ExamRoleChoiceButton(
                text = stringResource(R.string.examiner_instrumental),
                onClick = {/*TODO*/ }
            )
        }
    }
}

@Composable
fun ExamRoleChoiceButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        elevation = ButtonDefaults.elevation(defaultElevation = MaterialTheme.space.extraSmall),
        modifier = Modifier
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