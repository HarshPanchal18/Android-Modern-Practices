package com.example.modern_practices

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.modern_practices.ui.theme.ModernPracticesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ModernPracticesTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.FirstScreen) {
                    composable<Routes.FirstScreen> {
                        FirstScreen {
                            navController.navigate(
                                Routes.SecondScreen(primitive = "Custom Primitive Value")
                            )
                        }
                    }

                    composable<Routes.SecondScreen> { backstackEntry ->
                        val customVal = backstackEntry.toRoute<Routes.SecondScreen>()
                        Log.i("SecondScreen", customVal.primitive)
                        SecondScreen {
                            navController.navigate(Routes.FirstScreen)
                        }
                    }
                }
            }
        }
    }

}
