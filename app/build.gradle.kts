plugins {
    id("com.android.library") version "8.1.0"
    kotlin("android")
}

android {
    namespace = "com.harpa.logger"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
}
