plugins {
    alias(libs.plugins.pixelpost.android.library)
    alias(libs.plugins.pixelpost.kotlin.parcelize)
    alias(libs.plugins.pixelpost.android.library.compose)

}

android {
    namespace = "com.byteshop.core"
}

dependencies {
    // Additional dependencies specific to core module can be added here
}