// ✅ Berikut adalah daftar perubahan dan file yang akan kita update dalam proyek "tes-log-html" // agar menjadi modul LSPosed yang berfungsi penuh untuk logging Harpa WebView, Activity, dll per Mei 2025

// =========================== // 1. app/src/main/java/com/harpa/logger/MainHook.kt // Ganti isi dengan hook logging terbaru

package com.harpa.logger

import android.app.Application import android.app.Activity import android.webkit.WebView import android.webkit.ValueCallback import android.content.Intent import android.view.View import android.widget.Button import de.robv.android.xposed.IXposedHookLoadPackage import de.robv.android.xposed.XC_MethodHook import de.robv.android.xposed.XposedBridge import de.robv.android.xposed.XposedHelpers import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage { override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) { if (!lpparam.packageName.contains("harpa")) return

XposedBridge.log("[HarpaLogger] Package detected: ${lpparam.packageName}")

    XposedHelpers.findAndHookMethod(
        Application::class.java,
        "onCreate",
        object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                XposedBridge.log("[HarpaLogger] App onCreate")
            }
        }
    )

    XposedHelpers.findAndHookMethod(
        Activity::class.java,
        "onResume",
        object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                val activity = param.thisObject as Activity
                XposedBridge.log("[HarpaLogger] Activity Resumed: ${activity.localClassName}")
            }
        }
    )

    XposedHelpers.findAndHookMethod(
        WebView::class.java,
        "loadUrl",
        String::class.java,
        object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                XposedBridge.log("[HarpaLogger] WebView loadUrl: ${param.args[0]}")
            }
        }
    )

    XposedHelpers.findAndHookMethod(
        WebView::class.java,
        "evaluateJavascript",
        String::class.java,
        ValueCallback::class.java,
        object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                XposedBridge.log("[HarpaLogger] JS Eval: ${param.args[0]}")
            }
        }
    )

    XposedHelpers.findAndHookMethod(
        Intent::class.java,
        "getAction",
        object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                XposedBridge.log("[HarpaLogger] Intent action: ${param.result}")
            }
        }
    )
}

}

