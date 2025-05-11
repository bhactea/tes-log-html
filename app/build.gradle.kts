plugins {
  id("com.android.library")
  kotlin("android")
}

android {
  namespace   = "com.harpa.logger"
  compileSdk  = 33

  defaultConfig {
    minSdk    = 21
    targetSdk = 33
  }

  buildTypes {                      // ← PASTIKAN DI SINI
    release {
      isMinifyEnabled = false       // gunakan isMinifyEnabled
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
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
  compileOnly("de.robv.android.xposed:api:82")
}
