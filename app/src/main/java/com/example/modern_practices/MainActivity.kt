package com.example.modern_practices

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.modern_practices.ui.theme.ModernPracticesTheme
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModernPracticesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WebViewPage(
                        url = "https://www.boltuix.com/",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(url: String, modifier: Modifier) {

    val context = LocalContext.current
    val adServers = StringBuilder()
    var line: String
    val inputStream = context.resources.openRawResource(R.raw.adblockserverlist)
    val br = BufferedReader(InputStreamReader(inputStream))

    try {
        while (br.readLine().also { line = it } != null) {
            adServers.append(line)
            adServers.append("\n")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

    AndroidView(
        modifier = modifier,
        factory = {
            WebView(it).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true

                webViewClient = object : WebViewClient() {
                    override fun shouldInterceptRequest(
                        view: WebView?, request: WebResourceRequest?
                    ): WebResourceResponse? {

                        val empty = ByteArrayInputStream("".toByteArray())
                        val kk5 = adServers.toString()
                        if (kk5.contains(":::::" + request?.url?.host))
                            return WebResourceResponse("text/plain", "utf-8", empty)

                        return super.shouldInterceptRequest(view, request)
                    }
                }

                loadUrl(url)
            }
        },
        update = { view -> view.loadUrl(url) },
    )
}
