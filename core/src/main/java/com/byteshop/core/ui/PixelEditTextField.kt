package com.byteshop.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * TODO:// Replace with theme
 */
@Composable
fun PixelEditTextField(
    modifier: Modifier = Modifier,
    placeholderString: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    val shape = RoundedCornerShape(8.dp)
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Color(0xFFFAFAFA))
            .border(
                width = 1.dp, color = Color(0xFFDBDBDB), shape = shape
            )
            .padding(
                vertical = 16.dp, horizontal = 12.dp
            ),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 14.sp
        ),
        cursorBrush = SolidColor(Color.Black),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart

            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholderString,
                        color = Color(0xFF999999),
                        fontSize = 14.sp
                    )
                }
                innerTextField()
            }
        }

    )

}


@Preview(
    showBackground = true
)
@Composable
fun PixelEditTextFieldPreview() {
    var value by remember { mutableStateOf("") }

    val onValueChange = { newValue: String ->
        value = newValue
    }
    PixelEditTextField(
        placeholderString = "Username, email address, or phone number",
        value = value,
        onValueChange = onValueChange,
        isPassword = false
    )
}

@Preview(
    showBackground = true
)
@Composable
fun PixelEditTextFieldPreviewAsPassword() {
    var value by remember { mutableStateOf("") }

    val onValueChange = { newValue: String ->
        value = newValue
    }
    PixelEditTextField(
        placeholderString = "Username, email address, or phone number",
        value = value,
        onValueChange = onValueChange,
        isPassword = true
    )
}