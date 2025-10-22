plugins {
    alias(libs.plugins.pixelpost.android.library)
}

android {
    namespace = "com.byteshop.core"
}

dependencies {
    // Core module is now empty - UI components moved to core:ui
    // This module can be used for common utilities, extensions, etc.
}