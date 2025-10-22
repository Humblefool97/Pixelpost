package com.byteshop.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PixelPrimaryButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults
            .buttonColors()
            .copy(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),

        ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PixelPostOutlineButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFF0095F6)
        )
    ) {
        Text(
            text = text,
            color = Color(0xFF0095F6),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

/**
 * Preview for PrimaryButton
 */
@Preview(
    showBackground = true
)
@Composable
fun PrimaryButtonPreview() {
    PixelPrimaryButton(
        modifier = Modifier,
        text = "Primary Button",
        onClick = {}
    )
}

/**
 * Preview for PrimaryButton
 */
@Preview(
    showBackground = true
)
@Composable
fun PixelPostOutlineButtonPreview() {
    PixelPostOutlineButton(
        modifier = Modifier,
        text = "Primary Button",
        onClick = {}
    )
}