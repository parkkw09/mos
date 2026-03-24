import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.ksp)
}

val homePath: String = System.getProperty("user.home") ?: "/Users/home"
val propsFile = file("${homePath}/Documents/private/key/app_props.properties")
val localProps = Properties().apply {
    if (propsFile.exists()) {
        propsFile.inputStream().use { load(it) }
    }
}

android {
    namespace = "app.peter.mos"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "app.peter.mos"
        minSdk = 27
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        resValue("string", "seoul_key",
            localProps.getProperty("SEOUL_KEY") ?: "NOT_FOUND")
        resValue("string", "server_client_id",
            localProps.getProperty("GOOGLE_AUTH_KEY") ?: "NOT_FOUND")

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), file("proguard-rules.pro"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        resValues = true
    }
}

dependencies {
    // Modules
    implementation(project(":domain"))
    implementation(project(":data"))

    // Kotlin
    implementation(libs.coreKtx)
    implementation(platform(libs.kotlinBom))

    // Hilt
    implementation(libs.hiltAndroid)
    // Hilt compiler via KSP
    ksp(libs.hiltCompiler)

    // Lifecycle
    implementation(libs.lifecycleRuntimeKtx)

    // Splash Screen
    implementation(libs.coreSplashscreen)

    // Compose
    implementation(libs.activityCompose)
    implementation(platform(libs.composeBom))
    implementation(libs.composeUi)
    implementation(libs.composeUiGraphics)
    implementation(libs.composeUiToolingPreview)
    implementation(libs.material3)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidxTestExtJunit)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation(libs.composeUiTestJunit4)

    // Debug
    debugImplementation(libs.composeUiTooling)
    debugImplementation(libs.composeUiTestManifest)
}
