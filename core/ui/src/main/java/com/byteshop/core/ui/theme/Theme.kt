package com.byteshop.core.ui.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val lightColorScheme = lightColorScheme(
    // ============================================
    // PRIMARY - Instagram Blue (#0095F6)
    // Used for: Buttons, links, active states, follow buttons
    // ============================================
    primary = InstagramBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6EDFF),
    onPrimaryContainer = Color(0xFF001D35),

    // ============================================
    // SECONDARY - Gray for subtle actions
    // Used for: Secondary buttons, less important actions
    // ============================================
    secondary = LightTextSecondary,
    onSecondary = Color.White,
    secondaryContainer = LightSurface,
    onSecondaryContainer = LightTextPrimary,

    // ============================================
    // TERTIARY - Instagram Red (Heart/Like)
    // Used for: Like button, favorite actions
    // ============================================
    tertiary = InstagramRed,                          // #ED4956
    onTertiary = Color.White,                         // White on red
    tertiaryContainer = Color(0xFFFFDAD9),            // Light red container
    onTertiaryContainer = Color(0xFF410006),          // Dark text on light red

    // ============================================
    // ERROR - Same as Instagram Red
    // Used for: Error messages, destructive actions
    // ============================================
    error = ErrorRed,
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD9),
    onErrorContainer = Color(0xFF410006),
// ============================================
    // BACKGROUND - Pure White
    // The main screen canvas (Instagram uses pure white)
    // ============================================
    background = LightBackground,
    onBackground = LightTextPrimary,
    // ============================================
    // SURFACE - For cards, dialogs, sheets
    // ============================================
    surface = LightBackground,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurface,
    onSurfaceVariant = LightTextSecondary,
    // Surface containers (different elevation levels)
    surfaceContainer = LightCard,                     // #FAFAFA
    surfaceContainerHigh = LightSurface,              // #F0F0F0
    surfaceContainerHighest = Color(0xFFE6E6E6),      // Slightly darker
    surfaceContainerLow = Color(0xFFFAFAFA),          // Very subtle
    surfaceContainerLowest = Color.White,             // Pure white

    // ============================================
    // SURFACE TINTS (for elevation indication)
    // ============================================
    surfaceTint = InstagramBlue,                      // Tint color for elevated surfaces

    // ============================================
    // INVERSE COLORS (for snackbars, tooltips)
    // ============================================
    inverseSurface = LightIconPrimary,                // #262626
    inverseOnSurface = Color.White,                   // White on dark
    inversePrimary = InstagramBlue,                   // Blue stays same

    // ============================================
    // OUTLINE - Borders and dividers
    // ============================================
    outline = LightBorder,                            // #C7C7C7
    outlineVariant = LightOutline,                    // #DBDBDB (lighter)

    // ============================================
    // SCRIM - For dialogs/modals overlay
    // ============================================
    scrim = Color.Black.copy(alpha = 0.32f),          // Semi-transparent black
)

/**
 * Instagram Mobile App - Dark Theme Color Scheme
 * Pure black theme optimized for OLED displays (battery saving)
 */
@VisibleForTesting
val darkColorScheme = darkColorScheme(
    // ============================================
    // PRIMARY - Instagram Blue (stays same on dark)
    // ============================================
    primary = DarkInstagramBlue,                      // #0095F6 (same blue)
    onPrimary = Color.White,                          // White text
    primaryContainer = Color(0xFF004A77),             // Dark blue container
    onPrimaryContainer = Color(0xFFB8E1FF),           // Light text on dark blue

    // ============================================
    // SECONDARY - Light gray for subtle actions
    // ============================================
    secondary = DarkTextSecondary,                    // #A8A8A8
    onSecondary = DarkBackground,                     // Black on gray
    secondaryContainer = DarkBorder,                  // #262626
    onSecondaryContainer = DarkTextPrimary,           // #FFFFFF

    // ============================================
    // TERTIARY - Instagram Red (unchanged)
    // ============================================
    tertiary = InstagramRed,                          // #ED4956 (same)
    onTertiary = Color.White,                         // White on red
    tertiaryContainer = Color(0xFF5F0009),            // Dark red container
    onTertiaryContainer = Color(0xFFFFDAD9),          // Light text on dark red

    // ============================================
    // ERROR - Instagram Red
    // ============================================
    error = ErrorRed,                                 // #ED4956
    onError = Color.White,                            // White on red
    errorContainer = Color(0xFF5F0009),               // Dark red background
    onErrorContainer = Color(0xFFFFDAD9),             // Light text on dark red

    // ============================================
    // BACKGROUND - Pure Black (OLED optimized)
    // Instagram uses true black for battery savings
    // ============================================
    background = DarkBackground,                      // #000000 (pure black)
    onBackground = DarkTextPrimary,                   // #FFFFFF (true white)

    // ============================================
    // SURFACE - Slightly elevated from black
    // ============================================
    surface = DarkBackground,                         // #000000 (same as background)
    onSurface = DarkTextPrimary,                      // #FFFFFF
    surfaceVariant = DarkSurface,                     // #121212 (slightly elevated)
    onSurfaceVariant = DarkTextSecondary,             // #A8A8A8

    // Surface containers (elevation levels in dark mode)
    surfaceContainer = DarkSurface,                   // #121212
    surfaceContainerHigh = DarkSurfaceElevated,       // #1C1C1C
    surfaceContainerHighest = DarkSurfaceHighest,     // #262626
    surfaceContainerLow = Color(0xFF0A0A0A),          // Very subtle from black
    surfaceContainerLowest = DarkBackground,          // Pure black

    // ============================================
    // SURFACE TINTS
    // ============================================
    surfaceTint = DarkInstagramBlue,                  // Blue tint

    // ============================================
    // INVERSE COLORS
    // ============================================
    inverseSurface = Color.White,                     // White
    inverseOnSurface = DarkBackground,                // Black on white
    inversePrimary = InstagramBlue,                   // Blue

    // ============================================
    // OUTLINE - Borders in dark mode
    // ============================================
    outline = DarkBorder,                             // #262626
    outlineVariant = DarkDivider,                     // #363636

    // ============================================
    // SCRIM
    // ============================================
    scrim = Color.Black.copy(alpha = 0.6f),           // Darker scrim in dark mode
)
@Composable
fun PixelPostTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    disableDynamicTheming: Boolean = false,
    content: @Composable () -> Unit
) {
    //Color scheme selection
    val colorScheme = when {
        !disableDynamicTheming && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        else -> if (darkTheme) {
            darkColorScheme
        } else {
            lightColorScheme
        }
    }

    CompositionLocalProvider {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = PixelPostTypography,
            content = content
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S