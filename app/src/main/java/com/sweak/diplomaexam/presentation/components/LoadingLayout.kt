package com.sweak.diplomaexam.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sweak.diplomaexam.R
import com.sweak.diplomaexam.presentation.ui.theme.space

@Composable
fun LoadingLayout(
    modifier: Modifier = Modifier,
    loadingLayoutSize: LoadingLayoutSize = LoadingLayoutSize.LARGE,
    text: String = stringResource(R.string.loading)
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(
                    bottom =
                    if (loadingLayoutSize == LoadingLayoutSize.LARGE) {
                        MaterialTheme.space.large
                    } else {
                        MaterialTheme.space.medium
                    }
                )
        )

        CircularProgressIndicator(
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .size(
                    size = if (loadingLayoutSize == LoadingLayoutSize.LARGE) 96.dp else 48.dp
                )
        )
    }
}

enum class LoadingLayoutSize {
    SMALL, LARGE
}