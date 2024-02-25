package com.example.modern_practices

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = { navigator?.push(ProfileScreen) }) {
                Text(text = "Press")
            }
        }
    }
}

object ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = { navigator?.pop() }) {
                Text(text = "Press for Home")
            }
        }
    }
}
