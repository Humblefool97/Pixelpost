plugins {
    alias(libs.plugins.pixelpost.android.feature)
}

android {
    namespace = "com.byteshop.auth"
}

dependencies {
    // Feature module dependencies
    implementation(projects.core)
}