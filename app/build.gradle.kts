plugins {
  id("com.android.application")
  kotlin("android")
}

android {
  // … konfigurasi android …
}

dependencies {
  compileOnly("de.robv.android.xposed:api:82")
}

// 1. Explicit dependency
tasks.named("mergeDebugAssets") {
  dependsOn(
    "mergeDebugResources",
    "generateDebugResValues",
    "checkDebugAarMetadata",
    "javaPreCompileDebug",
    "createDebugCompatibleScreenManifests"
  )
}

// 2. (Opsional) Atur urutan
//tasks.named("mergeDebugAssets") {
//  mustRunAfter("mergeDebugResources", "generateDebugResValues")
//}
