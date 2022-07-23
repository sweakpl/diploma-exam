package com.example.egzamindyplomowy.presentation.introduction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.egzamindyplomowy.R
import com.example.egzamindyplomowy.presentation.WindowInfo
import com.example.egzamindyplomowy.presentation.rememberWindowInfo
import com.example.egzamindyplomowy.presentation.ui.theme.space

@Composable
fun WelcomeScreen() {
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactWelcomeScreen()
    } else {
        MediumOrExpandedWelcomeScreen()
    }
}

@Composable
fun CompactWelcomeScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
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
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.space.medium))

        Icon(
            painter = painterResource(id = R.drawable.ic_application_icon),
            contentDescription = "Application icon - university graduation cap",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .size(96.dp)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.space.medium))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                bottom = MaterialTheme.space.extraLarge,
                start = MaterialTheme.space.large,
                end = MaterialTheme.space.large
            )
        )

        Text(
            text = stringResource(R.string.welcome_to_exam),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                bottom = MaterialTheme.space.medium,
                start = MaterialTheme.space.large,
                end = MaterialTheme.space.large
            )
        )

        Text(
            text = stringResource(R.string.tell_me_who_you_are),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                bottom = MaterialTheme.space.large,
                start = MaterialTheme.space.large,
                end = MaterialTheme.space.large
            )
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

        Spacer(modifier = Modifier.height(MaterialTheme.space.large))
    }
}

@Composable
fun MediumOrExpandedWelcomeScreen() {
    Row(
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
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_application_icon),
                contentDescription = "Application icon - university graduation cap",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .size(96.dp)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.space.medium))

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    bottom = MaterialTheme.space.medium,
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large
                )
            )

            Text(
                text = stringResource(R.string.welcome_to_exam),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    bottom = MaterialTheme.space.medium,
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.tell_me_who_you_are),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    top = MaterialTheme.space.medium,
                    bottom = MaterialTheme.space.large,
                    start = MaterialTheme.space.large,
                    end = MaterialTheme.space.large
                )
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

            Spacer(modifier = Modifier.height(MaterialTheme.space.medium))
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
            .padding(
                start = MaterialTheme.space.large,
                end = MaterialTheme.space.large
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.primary
        )
    }
}