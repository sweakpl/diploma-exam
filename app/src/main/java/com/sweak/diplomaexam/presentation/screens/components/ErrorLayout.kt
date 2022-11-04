package com.sweak.diplomaexam.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun ErrorLayout(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = "Error icon",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .size(MaterialTheme.space.extraLarge)
                .padding(bottom = MaterialTheme.space.medium)
        )

        Text(
            text = stringResource(R.string.error_occurred),
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = MaterialTheme.space.large)
        )

        ThickWhiteButton(
            text = stringResource(R.string.retry),
            onClick = onRetryClick
        )
    }
}