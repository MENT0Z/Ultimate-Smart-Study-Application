package com.mruraza.smartstudy.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ButtonWithText(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean?=null,
    onClick: () -> Unit
) {
    Button(onClick = onClick, modifier = modifier,enabled = enabled?:true) {
        Text(text = text)
    }
}