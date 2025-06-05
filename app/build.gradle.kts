import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtoolsKsp)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.dagger.hilt)
}



android {
    namespace = "com.mbialowas.moviehubspr_int_2025"
    compileSdk = 35

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "com.squareup" && requested.name == "javapoet") {
                useVersion("1.13.0")
                because("Fix NoSuchMethodError for canonicalName() in Hilt plugin")
            }
        }
    }

    defaultConfig {
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(FileInputStream(localPropertiesFile))
        }

        val tmdbApiKey = localProperties.getProperty("TMDB_API_KEY") ?: ""
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")

        val googleApiKey = localProperties.getProperty("GOOGLE_API_KEY") ?: ""
        buildConfigField("String", "GOOGLE_API_KEY", "\"$googleApiKey\"")
        manifestPlaceholders["googleMapsApiKey"] = googleApiKey

        val serpApiKey = localProperties.getProperty("SERP_API_KEY") ?: ""
        buildConfigField("String", "SERP_API_KEY", "\"$serpApiKey\"")

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
        buildConfig = true
    }
}

dependencies {


    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:2.1.0")

    implementation("com.google.android.material:material:1.12.0")

    // ✅ Hilt setup (matching versions)
    implementation("com.google.dagger:hilt-android:2.51")
    ksp("com.google.dagger:hilt-compiler:2.51")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // JavaPoet compatibility fix for Hilt
    implementation("com.squareup:javapoet:1.13.0")

    // Google Maps & Location
    implementation(libs.maps.compose)
    implementation(libs.google.maps.sdk)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.places)

    // UI & Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Accompanist permissions
    implementation(libs.accompanist)

    // Moshi
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.common)
    ksp(libs.androidx.room.compiler)

    // Firebase
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.8.1")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
