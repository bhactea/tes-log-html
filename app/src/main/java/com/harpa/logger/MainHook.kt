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
    if (lpparam.packageName != "com.harpamobilehr" ||
        lpparam.processName != lpparam.packageName) return

    XposedBridge.log("HarpaLogger: hooking WebViewClient in ${lpparam.packageName}")

    try {
      val hookImpl = object : XC_MethodHook() {
        override fun afterHookedMethod(param: MethodHookParam) {
          val webView = param.args[0] as? WebView ?: return
          val url     = param.args[1] as? String ?: return
          if (!url.contains("harpamobilehr")) return

          webView.post {
            webView.evaluateJavascript(
              "(function(){return document.documentElement.outerHTML;})();"
            ) { html ->
              html?.takeIf { it.isNotBlank() }?.let { saveHtml(it) }
            }
          }
        }
      }

      XposedHelpers.findAndHookMethod(
        "android.webkit.WebViewClient",
        lpparam.classLoader,
        "onPageFinished",
        WebView::class.java,
        String::class.java,
        hookImpl
      )
      // Hook Chromium-based subclass
      XposedHelpers.findAndHookMethod(
        "androidx.webkit.internal.WebViewClientImpl",
        lpparam.classLoader,
        "onPageFinished",
        WebView::class.java,
        String::class.java,
        hookImpl
      )
    } catch (e: Throwable) {
      XposedBridge.log("HarpaLogger: failed to hook - ${e.message}")
    }
  }

  private fun saveHtml(html: String) {
    try {
      val ts    = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
      val dir   = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS)
      if (!dir.exists() || !dir.canWrite()) {
        // fallback ke filesDir jika perlu
      }
      val file  = File(dir, "harpa_html_$ts.html")
      FileOutputStream(file).use { it.write(html.toByteArray(Charsets.UTF_8)) }
      XposedBridge.log("HarpaLogger: saved HTML to ${file.absolutePath}")
    } catch (e: Exception) {
      XposedBridge.log("HarpaLogger: save error - ${e.message}")
    }
  }
}
