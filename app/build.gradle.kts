import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtoolsKsp)
    alias(libs.plugins.google.gms.google.services)

}



android {
    namespace = "com.mbialowas.moviehubspr_int_2025"
    compileSdk = 35




    defaultConfig {

        // Load TMDB_API_KEY from local.properties
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(FileInputStream(localPropertiesFile))
        }
        val tmdbApiKey = localProperties.getProperty("TMDB_API_KEY") ?: ""
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")

        val googleApiKey = localProperties.getProperty("GOOGLE_API_KEY") ?: ""
        buildConfigField("String", "GOOGLE_API_KEY", "\"$googleApiKey\"")

        // 👇 THIS LINE IS MISSING
        manifestPlaceholders["googleMapsApiKey"] = googleApiKey


        applicationId = "com.mbialowas.moviehubspr_int_2025"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        buildConfig = true // set for api keys
    }
}

dependencies {

    // Google Maps & Location
    implementation(libs.maps.compose)
    implementation(libs.google.maps.sdk) // Google Maps SDK
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    //google places
    implementation(libs.places)

    // required matierla components library required for Google Marker?
    implementation("com.google.android.material:material:1.12.0") // Use latest stable version




    // accompanist permissions
    implementation(libs.accompanist)


    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.common)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    ksp(libs.androidx.room.compiler)


    //coil compose
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    implementation("androidx.navigation:navigation-compose:2.8.1")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}