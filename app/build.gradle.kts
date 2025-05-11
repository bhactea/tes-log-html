plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.harpa.logger"    // ganti sesuai package di module.prop
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
        // jangan ada applicationId, versionCode, versionName di library
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("de.robv.android.xposed:api:82")
}
