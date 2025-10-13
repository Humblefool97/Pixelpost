package com.byteshop.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byteshop.core.ui.PixelEditTextField
import com.byteshop.core.ui.PixelPostOutlineButton
import com.byteshop.core.ui.PixelPrimaryButton
import com.byteshop.core.R as CoreResource

@Composable
internal fun AuthScreen(
    modifier: Modifier = Modifier,
    onAuthSuccess: () -> Unit = {},
) {

    Surface(
        modifier = Modifier.padding(16.dp)
    ) {
        Box {
            Body(
                modifier = modifier
                    .align(Alignment.TopCenter)
                    .fillMaxHeight(0.8f)
            )
            Footer(
                modifier = Modifier.align(alignment = Alignment.BottomCenter)
            )
        }
    }
}


@Composable
private fun Body(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Icon
        Image(
            painter = painterResource(id = CoreResource.drawable.vc_pp_auth_icon_small),
            contentDescription = "App icon",
            modifier = Modifier
                .size(40.dp),
            contentScale = ContentScale.Fit
        )
        //Spacer
        Spacer(
            modifier = Modifier.size(54.dp)
        )

        PixelEditTextField(
            placeholderString = "Username, email address, or phone number",
            value = "",
            onValueChange = {},
        )
        //Spacer
        Spacer(
            modifier = Modifier.size(8.dp)
        )
        PixelEditTextField(
            placeholderString = "Password",
            value = "",
            onValueChange = {},
        )
        //Spacer
        Spacer(
            modifier = Modifier.size(8.dp)
        )
        PixelPrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Log in",
            onClick = {}
        )
    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PixelPostOutlineButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Create a new account",
            onClick = {}
        )
    }
}


@Preview(
    showBackground = true
)
@Composable
fun BodyPreview() {
    Body()
}

@Preview(
    showBackground = true
)
@Composable
fun FooterPreview() {
    Footer()
}

@Preview(
    showBackground = true
)
@Composable
fun AuthScreenPreview() {
    AuthScreen()
}


