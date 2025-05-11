plugins {
  id("com.android.application")
  kotlin("android")
}

android {
  compileSdk = 33

  defaultConfig {
    applicationId = "com.harpa.logger"
    minSdk = 21
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"
  }
  // ... sisanya tetap sama ...
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
