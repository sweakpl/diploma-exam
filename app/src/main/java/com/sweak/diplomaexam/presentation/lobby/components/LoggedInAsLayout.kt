package com.sweak.diplomaexam.presentation.lobby.components

import androidx.compose.animation.AnimatedVisibility
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
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun LoggedInAsLayout(userEmail: String?) {
    AnimatedVisibility(
        visible = userEmail != null
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.space.large)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_account_circle),
                contentDescription = "Logged in account icon",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .size(MaterialTheme.space.medium)
                    .padding(end = MaterialTheme.space.extraSmall)
            )

            Text(
                text = stringResource(R.string.logged_in_as, userEmail ?: ""),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Start,
            )
        }
    }
}