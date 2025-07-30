package com.example.coffeehouse.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeehouse.R
import com.example.coffeehouse.ui.auth.components.EditablePlace


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    email: String,
    password: String,
    exceptionMessage: String,
    updateAuthState: () -> Unit,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    authUser: () -> Unit,
) {
    Scaffold(
        topBar = {
            Column() {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.login_title),
                            color = colorResource(id = R.color.brown_dark),
                            fontWeight = FontWeight.Bold,
                        )
                    },
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorResource(id = R.color.gray)
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorResource(id = R.color.gray),
                    modifier = Modifier.padding(top = 1.dp)
                )
            }
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .padding(start = 18.dp, end = 18.dp)
                        .align(Alignment.Center)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = exceptionMessage,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.Red,
                        fontSize = 18.sp
                    )
                    EditablePlace(
                        modifier = Modifier.padding(bottom = 24.dp),
                        label = stringResource(R.string.editable_title_email),
                        text = email,
                        updateText = updateEmail,
                    )
                    EditablePlace(
                        modifier = Modifier.padding(bottom = 24.dp),
                        label = stringResource(R.string.editable_title_password),
                        text = password,
                        updateText = updatePassword,
                        isPassword = true
                    )
                    Button(
                        onClick = {
                            authUser()
                        },
                        shape = RoundedCornerShape(24.5.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.button_brown))
                    ) {
                        Text(
                            text = stringResource(R.string.button_title_enter),
                            color = colorResource(id = R.color.brown_light)
                        )
                    }
                    TextButton(
                        onClick = {
                            updateAuthState()
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) { Text(stringResource(R.string.registration_button_desc)) }
                }
            }
        }
    )
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen("","","aboba is not exist",{},{},{}, {})
}