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

  
  // include module.prop yang berada di app/src/main/module.prop
  sourceSets["main"].resources.srcDir("src/main")
  // atau:
  // sourceSets["main"].resources.srcDirs("src/main", "src/main/assets")
}

  
dependencies {
  compileOnly("de.robv.android.xposed:api:82")
}
