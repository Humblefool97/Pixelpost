package com.byteshop.core.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * PixelPost Design System - Spacing & Dimensions
 *
 * Based on Material Design 3 spacing guidelines:
 * https://m3.material.io/foundations/layout/applying-layout/spacing
 */
object Dimensions {

    // ============================================
    // SPACING (Material Design 3 Standard)
    // ============================================

    /** 0dp - No spacing */
    val SpacingNone: Dp = 0.dp

    /** 4dp - Extra small spacing */
    val SpacingXSmall: Dp = 4.dp

    /** 8dp - Small spacing (standard small gap) */
    val SpacingSmall: Dp = 8.dp

    /** 12dp - Medium-small spacing */
    val SpacingMediumSmall: Dp = 12.dp

    /** 16dp - Medium spacing (standard spacing) */
    val SpacingMedium: Dp = 16.dp

    /** 24dp - Large spacing */
    val SpacingLarge: Dp = 24.dp

    /** 32dp - Extra large spacing */
    val SpacingXLarge: Dp = 32.dp

    /** 48dp - Extra extra large spacing */
    val SpacingXXLarge: Dp = 48.dp

    /** 64dp - Extra extra extra large spacing */
    val SpacingXXXLarge: Dp = 64.dp

    // ============================================
    // PADDING (For Components)
    // ============================================

    /** 8dp - Small padding */
    val PaddingSmall: Dp = 8.dp

    /** 12dp - Medium-small padding */
    val PaddingMediumSmall: Dp = 12.dp

    /** 16dp - Medium padding (standard) */
    val PaddingMedium: Dp = 16.dp

    /** 20dp - Medium-large padding */
    val PaddingMediumLarge: Dp = 20.dp

    /** 24dp - Large padding */
    val PaddingLarge: Dp = 24.dp

    /** 32dp - Extra large padding */
    val PaddingXLarge: Dp = 32.dp

    // ============================================
    // COMPONENT SIZES
    // ============================================

    /** 40dp - Small icon size */
    val IconSizeSmall: Dp = 40.dp

    /** 56dp - Medium icon size */
    val IconSizeMedium: Dp = 56.dp

    /** 72dp - Large icon size */
    val IconSizeLarge: Dp = 72.dp

    /** 48dp - Button minimum height */
    val ButtonHeightMin: Dp = 48.dp

    /** 56dp - Button standard height */
    val ButtonHeight: Dp = 56.dp

    /** 40dp - Text field standard height */
    val TextFieldHeightMin: Dp = 40.dp

    /** 56dp - Text field standard height */
    val TextFieldHeight: Dp = 56.dp

    // ============================================
    // CORNER RADIUS
    // ============================================

    /** 4dp - Small corner radius */
    val CornerRadiusSmall: Dp = 4.dp

    /** 8dp - Medium corner radius */
    val CornerRadiusMedium: Dp = 8.dp

    /** 12dp - Large corner radius */
    val CornerRadiusLarge: Dp = 12.dp

    /** 16dp - Extra large corner radius */
    val CornerRadiusXLarge: Dp = 16.dp

    /** 24dp - Extra extra large corner radius */
    val CornerRadiusXXLarge: Dp = 24.dp

    // ============================================
    // BORDERS
    // ============================================

    /** 1dp - Standard border width */
    val BorderWidth: Dp = 1.dp

    /** 2dp - Thick border width */
    val BorderWidthThick: Dp = 2.dp

    // ============================================
    // ELEVATION
    // ============================================

    /** 0dp - No elevation */
    val ElevationNone: Dp = 0.dp

    /** 1dp - Small elevation */
    val ElevationSmall: Dp = 1.dp

    /** 2dp - Medium elevation */
    val ElevationMedium: Dp = 2.dp

    /** 4dp - Large elevation */
    val ElevationLarge: Dp = 4.dp

    /** 8dp - Extra large elevation */
    val ElevationXLarge: Dp = 8.dp
}

/**
 * Screen-level padding for consistent margins
 */
object ScreenPadding {
    /** 16dp - Standard screen horizontal padding */
    val Horizontal: Dp = 16.dp

    /** 16dp - Standard screen vertical padding */
    val Vertical: Dp = 16.dp

    /** 24dp - Large screen horizontal padding (tablets) */
    val HorizontalLarge: Dp = 24.dp

    /** 24dp - Large screen vertical padding (tablets) */
    val VerticalLarge: Dp = 24.dp
}

/**
 * Type aliases for convenience
 */
typealias Spacing = Dimensions