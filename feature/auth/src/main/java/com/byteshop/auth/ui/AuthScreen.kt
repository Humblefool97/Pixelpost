package com.byteshop.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.byteshop.auth.viewmodel.AuthViewModel
import com.byteshop.core.ui.components.PixelEditTextField
import com.byteshop.core.ui.components.PixelPostOutlineButton
import com.byteshop.core.ui.components.PixelPrimaryButton
import com.byteshop.core.ui.R as CoreUiResource

@Composable
internal fun AuthScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(),
    onAuthSuccess: () -> Unit = {},
) {
    // ✅ Collect state from ViewModel
    val formState by viewModel.formState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.padding(16.dp)
    ) {
        Box {
            Body(
                modifier = modifier
                    .align(Alignment.TopCenter)
                    .fillMaxHeight(0.8f),
                username = formState.usernameOrEmailOrPhone,
                password = formState.password,
                isFormValid = formState.isFormValid,
                onUsernameChange = viewModel::onUsernameChange,
                onPasswordChange = viewModel::onPasswordChange,
                onLoginClick = {
                    viewModel.authenticateUser(
                        email = formState.usernameOrEmailOrPhone,
                        password = formState.password
                    )
                }
            )
            Footer(
                modifier = Modifier.align(alignment = Alignment.BottomCenter)
            )
        }
    }
}


@Composable
private fun Body(
    modifier: Modifier = Modifier,
    username: String,
    password: String,
    isFormValid: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Icon
        Image(
            painter = painterResource(id = CoreUiResource.drawable.vc_pp_auth_icon_small),
            contentDescription = "App icon",
            modifier = Modifier
                .size(40.dp),
            contentScale = ContentScale.Fit
        )
        //Spacer
        Spacer(
            modifier = Modifier.size(54.dp)
        )

        // ✅ Value comes from ViewModel state
        PixelEditTextField(
            placeholderString = "Username, email address, or phone number",
            value = username,
            onValueChange = onUsernameChange,
        )
        //Spacer
        Spacer(
            modifier = Modifier.size(8.dp)
        )

        // ✅ Value comes from ViewModel state
        PixelEditTextField(
            placeholderString = "Password",
            value = password,
            onValueChange = onPasswordChange,
            isPassword = true
        )
        //Spacer
        Spacer(
            modifier = Modifier.size(8.dp)
        )

        // ✅ Button enabled based on form validation
        PixelPrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Log in",
            onClick = onLoginClick,
            enabled = isFormValid
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
    Body(
        username = "",
        password = "",
        isFormValid = false,
        onUsernameChange = {},
        onPasswordChange = {},
        onLoginClick = {}
    )
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


