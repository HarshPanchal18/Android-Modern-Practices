package com.example.modern_practices

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import com.example.modern_practices.ui.theme.ModernPracticesTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ModernPracticesTheme {
                Surface {
                }
            }
        }
    }
}
