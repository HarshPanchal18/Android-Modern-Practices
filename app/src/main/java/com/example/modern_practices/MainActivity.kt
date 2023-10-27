package com.example.modern_practices

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.modern_practices.ui.theme.ModernPracticesTheme

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
                        "file:///android_asset/index.html",
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
    AndroidView(factory = {
        WebView(it).apply {
            /*layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )*/
            webViewClient = WebViewClient()

            settings.javaScriptEnabled = true
            // Verify that the client requesting your web page is actually your Android app.
            settings.userAgentString = System.getProperty("http.agent")

            loadUrl(url)
        }
    }, update = { it.loadUrl(url) },
        modifier = modifier
    )
}
