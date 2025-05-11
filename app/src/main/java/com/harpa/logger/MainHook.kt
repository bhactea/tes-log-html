package com.harpa.logger

import android.os.Environment
import android.webkit.WebView
import android.webkit.WebViewClient
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        // Filter hanya untuk aplikasi target
        if (lpparam.packageName != "com.harpamobilehr") return

        XposedBridge.log("HarpaLogger: Hook aktif di ${lpparam.packageName}")

        try {
            XposedHelpers.findAndHookMethod(
                "android.webkit.WebViewClient",
                lpparam.classLoader,
                "onPageFinished",
                WebView::class.java,
                String::class.java,
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val webView = param.args[0] as? WebView ?: return
                        webView.evaluateJavascript(
                            "(function() { return document.documentElement.outerHTML; })();"
                        ) { html ->
                            saveHtmlToFile(html)
                        }
                    }
                }
            )
        } catch (e: Throwable) {
            XposedBridge.log("HarpaLogger: Gagal hook WebViewClient - ${e.message}")
        }
    }

    private fun saveHtmlToFile(html: String) {
        try {
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val fileName = "harpa_html_$timestamp.html"
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadsDir.exists()) downloadsDir.mkdirs()

            val file = File(downloadsDir, fileName)
            FileOutputStream(file).use { it.write(html.toByteArray()) }

            XposedBridge.log("HarpaLogger: HTML disimpan ke ${file.absolutePath}")
        } catch (e: Exception) {
            XposedBridge.log("HarpaLogger: Error simpan HTML - ${e.message}")
        }
    }
}
