package com.byteshop.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteshop.core.ui.theme.PixelPostTheme

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
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun PixelPrimaryLoadingIndicatorButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    showProgress: Boolean = false
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        PixelPrimaryButton(
            modifier = modifier,
            text = text,
            onClick = onClick,
            enabled = enabled
        )

        if (showProgress)
            CircularProgressIndicator(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp,
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
            color = MaterialTheme.colorScheme.primary
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
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

@Preview(
    showBackground = true
)
@Composable
fun PixelPostLoadingIndicatorButtonPreview() {
    PixelPrimaryLoadingIndicatorButton(
        modifier = Modifier,
        text = "Primary Button",
        onClick = {},
        showProgress = true
    )
}
@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PixelPrimaryButtonPreview() {
    PixelPostTheme {
        PixelPrimaryButton(
            modifier = Modifier,
            text = "Primary Button",
            onClick = {}
        )
    }
}