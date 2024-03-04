package com.example.modern_practices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
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
                    TabNavigator(HomeTab) {
                        Scaffold(
                            bottomBar = {
                                NavigationBar {
                                    TabNavigationItem(tab = HomeTab)
                                    TabNavigationItem(tab = InfoTab)
                                    TabNavigationItem(tab = ProfileTab)
                                }
                            }
                        ) {
                            Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
                                CurrentTab()
                            }
                        }
                    }
                }
            }
        }
    }
}
