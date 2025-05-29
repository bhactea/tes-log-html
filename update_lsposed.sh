#!/bin/bash

# Ganti MainHook.kt
cat > app/src/main/java/com/harpa/logger/MainHook.kt <<'EOF'
package com.harpa.logger

import android.webkit.WebView
import android.app.Activity
import android.os.Bundle
import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage
import de.robv.android.xposed.XposedHelpers

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.harpa.ai") return

        XposedBridge.log("📌 HARPA loaded: ${lpparam.packageName}")

        XposedHelpers.findAndHookMethod(
            "android.webkit.WebView", lpparam.classLoader,
            "loadUrl", String::class.java,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    val url = param.args[0] as? String
                    XposedBridge.log("🌐 WebView.loadUrl = $url")
                }
            }
        )

        XposedHelpers.findAndHookMethod(
            "android.webkit.WebView", lpparam.classLoader,
            "evaluateJavascript", String::class.java, android.webkit.ValueCallback::class.java,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    val js = param.args[0] as? String
                    XposedBridge.log("📥 evaluateJavascript: $js")
                }
            }
        )
    }
}
EOF

# Ganti module.prop
cat > app/src/main/assets/module.prop <<'EOF'
id=harpa.logger
name=Harpa Logger (WebView Hook)
version=1.0
versionCode=1
author=Bhakti Setyawan
description=Modul log WebView & JS untuk Harpa (React Native)
minMagisk=24
EOF

# Ganti AndroidManifest.xml
cat > app/src/main/AndroidManifest.xml <<'EOF'
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.harpa.logger"
    android:versionCode="1"
    android:versionName="1.0" >

    <application
        android:allowBackup="false"
        android:label="HarpaLogger"
        android:hasCode="false"/>
</manifest>
EOF

# Ganti build.gradle.kts
cat > app/build.gradle.kts <<'EOF'
plugins {
    id("com.android.library")
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
EOF

# Ganti settings.gradle.kts
echo 'rootProject.name = "HarpaLogger"' > settings.gradle.kts

# Hapus file yang tidak diperlukan
rm -f app/proguard-rules.pro
rm -f module.prop

# Commit & push ke GitHub
git add .
git commit -m "🔥 Update full LSPosed-compatible logging module"
git push
