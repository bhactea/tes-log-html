plugins {
  id("com.android.application")
  kotlin("android")
}

android {
  namespace       = "com.harpa.logger"
  compileSdk      = 33

  defaultConfig {
    applicationId = "com.harpa.logger"
    minSdk        = 21
    targetSdk     = 33
    versionCode   = 1
    versionName   = "1.0"
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
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }

  // Jika module.prop di root proyek:
  sourceSets["main"].assets.srcDir(rootProject.file("module.prop").parent)
}
