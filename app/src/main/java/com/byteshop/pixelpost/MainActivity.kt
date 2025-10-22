package com.byteshop.pixelpost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.byteshop.core.ui.Dimensions
import com.byteshop.pixelpost.navigation.PixelPostNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PixelPostNavHost(
                modifier = Modifier
                    .padding(Dimensions.PaddingMediumSmall)
                    .fillMaxWidth()
            )
        }
    }
}