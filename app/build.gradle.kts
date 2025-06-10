plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.interfaz_tfg"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.interfaz_tfg"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.testing.android)
    implementation(libs.androidx.ui.test.junit4.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("com.google.accompanist:accompanist-navigation-animation:0.34.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.6")

    implementation("com.auth0:java-jwt:4.4.0")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("io.coil-kt:coil-compose:2.4.0")

    // DataStore Preferences
    implementation("androidx.datastore:datastore-preferences:1.1.7")
    // optional - RxJava2 support
    implementation("androidx.datastore:datastore-preferences-rxjava2:1.1.7")
    // optional - RxJava3 support
    implementation("androidx.datastore:datastore-preferences-rxjava3:1.1.7")

    androidTestImplementation( "org.mockito:mockito-android:4.11.0")
    androidTestImplementation( "org.mockito.kotlin:mockito-kotlin:4.1.0")
    androidTestImplementation( "androidx.navigation:navigation-testing:2.7.7")


}