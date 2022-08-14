package com.example.egzamindyplomowy.presentation.login.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.egzamindyplomowy.R
import com.example.egzamindyplomowy.common.UserRole
import com.example.egzamindyplomowy.presentation.components.ThickWhiteButton
import com.example.egzamindyplomowy.presentation.ui.theme.space

@Composable
fun UserRoleChoiceButtons(onUserRoleChosen: (UserRole) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            onClick = { onUserRoleChosen(UserRole.USER_STUDENT) }
        )

        Spacer(modifier = Modifier.height(MaterialTheme.space.large))

        ThickWhiteButton(
            text = stringResource(R.string.examiner_instrumental),
            onClick = { onUserRoleChosen(UserRole.USER_EXAMINER) }
        )
    }
}