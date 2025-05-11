android {
    compileSdk = 33  // ➜ DSL Kotlin menggunakan properti `compileSdk` :contentReference[oaicite:5]{index=5}

    defaultConfig {
        applicationId = "com.harpa.logger"
        minSdk = 21
        targetSdk = 33  // ➜ Properti `targetSdk` di Kotlin DSL :contentReference[oaicite:6]{index=6}
        versionCode = 1
        versionName = "1.0"
    }

    // Opsi build tools
    buildToolsVersion = "34.0.0"
}
