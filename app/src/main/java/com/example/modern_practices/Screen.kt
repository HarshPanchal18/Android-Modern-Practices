package com.example.modern_practices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SimpleScreen(text: String, textButton: String, onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = text)
        Button(onClick = onClick) {
            Text(text = textButton)
        }
    }
}

@Composable
fun FirstScreen(modifier: Modifier = Modifier, onNavigateForward: () -> Unit) {
    SimpleScreen(text = "First", textButton = "Go Forward") {
        onNavigateForward()
    }
}

@Composable
fun SecondScreen(modifier: Modifier = Modifier, onNavigateForward: () -> Unit) {
    SimpleScreen(text = "Second", textButton = "Go Backward") {
        onNavigateForward()
    }
}
