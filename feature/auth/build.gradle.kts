import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.pixelpost.android.feature)
    alias(libs.plugins.pixelpost.android.firebase.auth)
    alias(libs.plugins.pixelpost.kotlin.parcelize)
    alias(libs.plugins.pixelpost.android.library.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.byteshop.auth"
}

dependencies {
    // Feature module dependencies
    implementation(projects.core.ui)
    implementation(projects.core.data)
    implementation(projects.core.network)
    //Dependency for compose navigation
    implementation(libs.androidx.navigation.compose)

}