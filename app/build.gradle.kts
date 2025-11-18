plugins {
    alias(libs.plugins.pixelpost.android.application)
    alias(libs.plugins.pixelpost.android.application.compose)
    alias(libs.plugins.pixelpost.android.application.firebase)
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
    implementation(projects.core.ui)
    implementation(projects.feature.auth)
    //Dependency for compose navigation
    implementation(libs.androidx.navigation.compose)
}