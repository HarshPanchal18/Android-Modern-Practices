package com.example.modern_practices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modern_practices.ui.theme.ModernPracticesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ModernPracticesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShowSnackBar()
                }
            }
        }
    }
}

@Composable
fun ShowSnackBar(alignValue: Alignment = Alignment.BottomCenter) {

    val snackBarHostState = remember { SnackbarHostState() }
    val message = "Whatever you want!"

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            snackBarHostState.showSnackbar(message)
        }
    }

    SnackbarHost(hostState = snackBarHostState) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = message,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .padding(vertical = 20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .graphicsLayer { shadowElevation = 5F }
                    .background(color = Color.LightGray)
                    .padding(vertical = 10.dp)
                    .align(alignValue),
                //color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
