package com.sweak.diplomaexam.presentation.screens.login.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.domain.model.common.UserRole
import com.sweak.diplomaexam.presentation.screens.common.components.ThickWhiteButton
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun UserRoleChoiceButtons(
    onUserRoleChosen: (UserRole) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.tell_me_who_you_are),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = MaterialTheme.space.large)
        )

        ThickWhiteButton(
            text = stringResource(R.string.student_instrumental),
            onClick = { onUserRoleChosen(UserRole.USER_STUDENT) },
            modifier = Modifier.padding(bottom = MaterialTheme.space.large)
        )


        ThickWhiteButton(
            text = stringResource(R.string.examiner_instrumental),
            onClick = { onUserRoleChosen(UserRole.USER_EXAMINER) }
        )
    }
}