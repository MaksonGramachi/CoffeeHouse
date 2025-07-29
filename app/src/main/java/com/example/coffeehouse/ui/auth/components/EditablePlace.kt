package com.example.coffeehouse.ui.auth.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeehouse.R

@Composable
fun EditablePlace(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    updateText: (String) -> Unit,
    isPassword: Boolean = false,
) {
    val color = colorResource(id = R.color.brown_dark)
    val textStyle = TextStyle(fontSize = 18.sp, color = color)

    Column {
        Text(text = label, fontSize = 15.sp, color = color)
        Spacer(modifier = Modifier.size(10.dp))
        Box(modifier
            .fillMaxWidth()
            .height(47.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(47.dp)
            ) {
                drawRoundRect(
                    color = color,
                    cornerRadius = CornerRadius(x = 24.5.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx()),
                )
            }
            BasicTextField(
                value = text, onValueChange = { updateText(it) },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 18.dp), textStyle = textStyle,
                visualTransformation = if(isPassword) PasswordVisualTransformation(mask = '\u002A') else VisualTransformation.None,
                singleLine = true
            )
        }
    }
}