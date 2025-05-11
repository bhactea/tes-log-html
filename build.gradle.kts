plugins {
  // tidak auto-apply, hanya mendeklarasikan
  id("com.android.library") version "8.1.0" apply false
  kotlin("android") version "1.9.0" apply false
}

allprojects {
  repositories {
    google()
    mavenCentral()
    // Xposed API
    maven { url = uri("https://api.xposed.info/") }
  }
}
