package com.example.modern_practices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.modern_practices.container.Component
import com.example.modern_practices.ui.theme.ModernPracticesTheme

class MainActivity : ComponentActivity() {

    private val component = Component()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModernPracticesTheme {
                Surface {
                    component.car.getCar()
                }
            }
        }
    }
}
