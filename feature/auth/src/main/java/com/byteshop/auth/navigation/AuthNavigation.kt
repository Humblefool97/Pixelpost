package com.byteshop.auth.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.byteshop.auth.ui.AuthScreen
import kotlinx.serialization.Serializable

@Serializable
data object AuthRoute

fun NavController.navigateToAuth(navOptions: NavOptions? = null) {
    navigate(route = AuthRoute, navOptions = navOptions)
}

fun NavGraphBuilder.authScreen(
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable<AuthRoute> {
        AuthScreen(
            onAuthSuccess = onAuthSuccess,
            modifier = modifier
        )
    }
}