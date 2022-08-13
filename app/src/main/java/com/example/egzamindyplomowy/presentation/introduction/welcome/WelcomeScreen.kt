package com.example.egzamindyplomowy.presentation.introduction.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.egzamindyplomowy.R
import com.example.egzamindyplomowy.common.LOGIN_MODE_EXAMINER
import com.example.egzamindyplomowy.common.LOGIN_MODE_STUDENT
import com.example.egzamindyplomowy.presentation.Screen
import com.example.egzamindyplomowy.presentation.WindowInfo
import com.example.egzamindyplomowy.presentation.components.ThickWhiteButton
import com.example.egzamindyplomowy.presentation.components.WelcomeLayout
import com.example.egzamindyplomowy.presentation.rememberWindowInfo
import com.example.egzamindyplomowy.presentation.ui.theme.space

@Composable
fun WelcomeScreen(
    navController: NavController
) {
    val windowInfo = rememberWindowInfo()

    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        CompactWelcomeScreen(navController = navController)
    } else {
        MediumOrExpandedWelcomeScreen(navController = navController)
    }
}

@Composable
fun CompactWelcomeScreen(navController: NavController) {
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
        WelcomeLayout()

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

        ThickWhiteButton(
            text = stringResource(R.string.student_instrumental),
            onClick = {
                navController.navigate(Screen.LoginScreen.withArguments(LOGIN_MODE_STUDENT))
            }
        )

        Spacer(modifier = Modifier.height(MaterialTheme.space.large))

        ThickWhiteButton(
            text = stringResource(R.string.examiner_instrumental),
            onClick = {
                navController.navigate(Screen.LoginScreen.withArguments(LOGIN_MODE_EXAMINER))
            }
        )

        Spacer(modifier = Modifier.height(MaterialTheme.space.large))
    }
}

@Composable
fun MediumOrExpandedWelcomeScreen(navController: NavController) {
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
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            WelcomeLayout()
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
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

            ThickWhiteButton(
                text = stringResource(R.string.student_instrumental),
                onClick = {
                    navController.navigate(Screen.LoginScreen.withArguments(LOGIN_MODE_STUDENT))
                }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.space.large))

            ThickWhiteButton(
                text = stringResource(R.string.examiner_instrumental),
                onClick = {
                    navController.navigate(Screen.LoginScreen.withArguments(LOGIN_MODE_EXAMINER))
                }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.space.medium))
        }
    }
}