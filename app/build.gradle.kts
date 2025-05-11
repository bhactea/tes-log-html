plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.harpa.logger"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
}

dependencies {
    // Xposed API sebagai compileOnly (tidak di-include di APK final)
    compileOnly("de.robv.android.xposed:api:82")
}
