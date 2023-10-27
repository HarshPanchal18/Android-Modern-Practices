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
    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()

            settings.javaScriptEnabled = true

            loadUrl(url)

            webViewClient = object: WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    removeElement(view)
                }

                private fun removeElement(view: WebView?) {
                    //view?.loadUrl("javascript:(function() { document.getElementById('blog-pager').style.display='none';})()")

                    view?.loadUrl("javascript:(function() { document.getElementsByClassName('btn')[0].style.display='none';})()")
                    view?.loadUrl("javascript:(function() { document.getElementsByClassName('btn')[1].style.display='none';})()")
                }
            }
        }
    }, update = { it.loadUrl(url) },
        modifier = modifier
    )
}
