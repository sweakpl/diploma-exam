package com.sweak.diplomaexam.presentation.lobby.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun LoggedInAsLayout(
    userEmail: String?,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = userEmail != null,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_account_circle),
                contentDescription = "Logged in account icon",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier.size(size = MaterialTheme.space.medium)
            )

            Text(
                text = stringResource(R.string.logged_in_as, userEmail ?: ""),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = MaterialTheme.space.extraSmall)
            )
        }
    }
}