package com.example.modern_practices

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
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
    val mutableStateTrigger: MutableState<Boolean> = remember { mutableStateOf(false) }

    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()

            settings.javaScriptEnabled = true

            webViewClient = object : WebViewClient() {

                // override the logic in WebView when the return value is true,
                // and call the system browser or third-party browser when it is false.
                @Deprecated("Deprecated in Java")
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    if (url.contains("facebook.com")) {
                        mutableStateTrigger.value = true
                        return true
                    }
                    return false
                }
            }

            loadUrl(url)
        }
    }, update = { it.loadUrl(url) },
        modifier = modifier
    )

    if (mutableStateTrigger.value)
        WebViewPage(url = "https://www.instagram.com/imharsh.18", modifier = modifier)
}
