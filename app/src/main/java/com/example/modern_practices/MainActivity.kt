package com.example.modern_practices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.modern_practices.ui.theme.ModernPracticesTheme
import com.example.modern_practices.view.CounterScreen
import com.example.modern_practices.viewmodel.CounterViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ModernPracticesTheme {
                val counterViewModel: CounterViewModel by viewModels()
                CounterScreen(viewModel = counterViewModel)
            }
        }
    }
}
