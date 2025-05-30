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
