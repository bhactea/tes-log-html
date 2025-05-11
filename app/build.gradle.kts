plugins {
  id("com.android.library")
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
  buildToolsVersion = "34.0.0"
}

dependencies {
  implementation("de.robv.android.xposed:api:82")
}
