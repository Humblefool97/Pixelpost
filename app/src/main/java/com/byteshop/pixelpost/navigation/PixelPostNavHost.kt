package com.byteshop.pixelpost.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.byteshop.auth.navigation.AuthRoute
import com.byteshop.auth.navigation.authScreen

@Composable
fun PixelPostNavHost(
    modifier: Modifier,
    startDestination: Any = AuthRoute
) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authScreen(
            modifier = modifier,
            onAuthSuccess = {

            }
        )
    }

}