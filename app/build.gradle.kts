plugins {
    alias(libs.plugins.pixelpost.android.application)
    alias(libs.plugins.pixelpost.android.application.compose)
    alias(libs.plugins.gms)
}

android {
    namespace = "com.byteshop.pixelpost"
    defaultConfig {
        applicationId = "com.byteshop.pixelpost"
    }
}
 
dependencies {
    // Module dependencies
    implementation(projects.core)
    implementation(projects.feature.auth)
}