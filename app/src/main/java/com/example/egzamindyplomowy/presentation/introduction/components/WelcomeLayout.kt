package com.example.egzamindyplomowy.presentation.introduction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.egzamindyplomowy.R
import com.example.egzamindyplomowy.presentation.ui.theme.space

@Composable
fun WelcomeLayout() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Application icon - university graduation cap",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(96.dp)
        )

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
    }
}