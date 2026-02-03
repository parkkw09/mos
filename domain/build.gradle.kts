plugins {
    // Java library plugin doesn't have a plugin id in TOML sample; keep java-library as-is and kotlinJvm via alias
    id("java-library")
    alias(libs.plugins.kotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // Kotlin
    implementation(libs.kotlinStdlib)

    // Coroutines
    implementation(libs.coroutinesCore)

    // Javax Inject
    implementation(libs.javaxInject)
}
