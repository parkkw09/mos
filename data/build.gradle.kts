plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "app.peter.mos.data"
    compileSdk = 36

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    testOptions {
        this.unitTests.isIncludeAndroidResources = false // placeholder; verify original semantics
    }
}

dependencies {
    implementation(project(":domain"))

    // Kotlin
    implementation(libs.coreKtx)
    implementation(libs.kotlinStdlib)

    // Hilt
    implementation(libs.hiltAndroid)
    // Hilt compiler via KSP
    ksp(libs.hiltCompiler)

    // Coroutines
    implementation(libs.coroutinesAndroid)

    // Ktor
    implementation(libs.ktorClientCore)
    implementation(libs.ktorClientCio)
    implementation(libs.ktorClientLogging)
    implementation(libs.ktorSerializationGson)
    implementation(libs.ktorClientContentNegotiation)

    // Gson
    implementation(libs.gson)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidxTestExtJunit)
    androidTestImplementation(libs.espressoCore)
}
