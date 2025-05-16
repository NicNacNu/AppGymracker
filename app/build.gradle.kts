
plugins {
    id("org.jetbrains.kotlin.android") version "2.1.10"
    id("kotlin-kapt")
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.gymtracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gymtracker"
        minSdk = 25
        targetSdk = 34
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

    buildFeatures{

        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

   // implementation("androidx.room:room-common:2.6.1")
   // kapt("androidx.room:room-compiler:2.6.1") // Nutze ksp für Annotation Processing
   // implementation("androidx.room:room-ktx:2.6.1")

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}