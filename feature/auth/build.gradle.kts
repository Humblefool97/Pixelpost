plugins {
    alias(libs.plugins.pixelpost.android.feature)
    alias(libs.plugins.pixelpost.android.firebase.auth)
    alias(libs.plugins.pixelpost.kotlin.parcelize)
    alias(libs.plugins.pixelpost.android.library.compose)
}

android {
    namespace = "com.byteshop.auth"
}

dependencies {
    // Feature module dependencies
    implementation(projects.core)
}