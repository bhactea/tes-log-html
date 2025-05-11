// Root-level Gradle settings: daftarkan plugin Android & Kotlin tapi jangan auto-apply
plugins {
  // Android Gradle Plugin
  id("com.android.library") version "8.1.0" apply false
  // Kotlin Android Plugin
  kotlin("android") version "1.9.0" apply false
}

// (Opsional) konfigurasi buildscript atau repositori tambahan
