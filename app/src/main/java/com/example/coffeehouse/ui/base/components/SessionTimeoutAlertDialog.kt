package com.example.coffeehouse.ui.base.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.coffeehouse.R

@Composable
fun SessionTimeoutAlertDialog(moveToLoginScreen: () -> Unit) {
    AlertDialog(
        onDismissRequest = { moveToLoginScreen() },
        title = { Text(text = stringResource(R.string.timeout_title)) },
        text = {
            Text(
                text = stringResource(R.string.timeout_desc),
                style = TextStyle(fontSize = 16.sp)
            )
        },
        confirmButton = {
            Button(onClick = moveToLoginScreen) {
                Text(text = stringResource(R.string.button_title_enter))
            }
        },
    )
}