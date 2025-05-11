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
    if (lpparam.packageName != "com.harpamobilehr") return
    XposedBridge.log("HarpaLogger: hook WebViewClient in ${lpparam.packageName}")

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
            webView.evaluateJavascript("(function(){return document.documentElement.outerHTML;})();") { html ->
              saveHtml(html)
            }
          }
        }
      )
    } catch (e: Throwable) {
      XposedBridge.log("HarpaLogger: hook failed: ${e.message}")
    }
  }

  private fun saveHtml(html: String) {
    try {
      val ts = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
      val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
      if (!dir.exists()) dir.mkdirs()
      val file = File(dir, "harpa_html_$ts.html")
      FileOutputStream(file).use { it.write(html.toByteArray()) }
      XposedBridge.log("HarpaLogger: saved HTML to ${file.absolutePath}")
    } catch (e: Exception) {
      XposedBridge.log("HarpaLogger: save error: ${e.message}")
    }
  }
}
