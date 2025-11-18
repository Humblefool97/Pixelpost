package com.byteshop.core.ui.theme

import androidx.compose.ui.graphics.Color

// ============================================
// INSTAGRAM MOBILE APP COLORS
// Reverse-engineered from actual Instagram Android/iOS app
// ============================================

// ============================================
// LIGHT MODE - Instagram Mobile App
// ============================================

// Primary Interactive Color
internal val InstagramBlue = Color(0xFF0095F6)     // Links, buttons, active states

// Backgrounds & Surfaces (Light Mode)
internal val LightBackground = Color(0xFFFFFFFF)   // Main background - pure white
internal val LightSurface = Color(0xFFF0F0F0)      // Subtle gray surface
internal val LightCard = Color(0xFFFAFAFA)         // Cards, elevated elements

// Borders & Separators (Light Mode)
internal val LightBorder = Color(0xFFC7C7C7)       // Default borders
internal val LightDivider = Color(0xFFDEDEDE)      // Dividers, separators
internal val LightOutline = Color(0xFFDBDBDB)      // Outlines

// Text & Icons (Light Mode)
internal val LightTextPrimary = Color(0xFF000000)  // Primary text - true black
internal val LightTextSecondary = Color(0xFF737373) // Secondary text
internal val LightTextTertiary = Color(0xFF8E8E8E)  // Tertiary/hint text
internal val LightTextDisabled = Color(0xFFC7C7C7)  // Disabled text
internal val LightIconPrimary = Color(0xFF262626)   // Primary icons
internal val LightIconSecondary = Color(0xFF8E8E8E) // Secondary icons

// ============================================
// DARK MODE - Instagram Mobile App
// Pure Black Theme (OLED Optimized)
// ============================================

// Primary Interactive Color (Dark Mode) - stays same
internal val DarkInstagramBlue = Color(0xFF0095F6)

// Backgrounds & Surfaces (Dark Mode)
internal val DarkBackground = Color(0xFF000000)    // Pure black - OLED friendly
internal val DarkSurface = Color(0xFF121212)       // Slightly elevated surface
internal val DarkSurfaceElevated = Color(0xFF1C1C1C) // Cards, more elevation
internal val DarkSurfaceHighest = Color(0xFF262626) // Highest elevation

// Borders & Separators (Dark Mode)
internal val DarkBorder = Color(0xFF262626)        // Default borders
internal val DarkDivider = Color(0xFF363636)       // Dividers, separators
internal val DarkOutline = Color(0xFF363636)       // Outlines

// Text & Icons (Dark Mode)
internal val DarkTextPrimary = Color(0xFFFFFFFF)   // Primary text - true white
internal val DarkTextSecondary = Color(0xFFA8A8A8) // Secondary text
internal val DarkTextTertiary = Color(0xFF737373)  // Tertiary/hint text
internal val DarkTextDisabled = Color(0xFF4A4A4A)  // Disabled text
internal val DarkIconPrimary = Color(0xFFFAFAFA)   // Primary icons
internal val DarkIconSecondary = Color(0xFFA8A8A8) // Secondary icons

// ============================================
// SEMANTIC COLORS - Both Modes
// ============================================

// Like/Heart (Instagram's signature red)
internal val InstagramRed = Color(0xFFED4956)

// Error States
internal val ErrorRed = Color(0xFFED4956)          // Same as heart red

// Success States
internal val SuccessGreen = Color(0xFF4CB848)

// Warning States
internal val WarningYellow = Color(0xFFFBBC04)

// Info/Link (uses primary blue)
internal val InfoBlue = Color(0xFF0095F6)

// Follow Button (alternative blue - sometimes slightly different)
internal val FollowBlue = Color(0xFF0095F6)

// Video/Reel indicator
internal val VideoBlue = Color(0xFF0095F6)

// Verified Badge Blue
internal val VerifiedBlue = Color(0xFF0095F6)

// ============================================
// INSTAGRAM BRAND GRADIENT (For logo/special effects only)
// NOT used in regular UI - only for branding
// ============================================
internal val GradientRoyalBlue = Color(0xFF405DE6)
internal val GradientPurple = Color(0xFF833AB4)
internal val GradientPink = Color(0xFFC13584)
internal val GradientRed = Color(0xFFE1306C)
internal val GradientOrange = Color(0xFFF77737)
internal val GradientYellow = Color(0xFFFCAF45)