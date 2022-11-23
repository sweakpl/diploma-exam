package com.sweak.diplomaexam.presentation.screens.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun WelcomeLayout(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.welcome_to_exam)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "DiplomaExamApplication icon - university graduation cap",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(96.dp)
        )

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = MaterialTheme.space.extraLarge)
        )

        Text(
            text = text,
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center
        )
    }
}