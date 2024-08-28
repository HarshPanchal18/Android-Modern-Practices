package com.example.modern_practices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            NavigationScaffold()
        }

    }
}

@OptIn(ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)
//@Preview
@Composable
fun NavigationScaffold() {

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val navItems = listOf("Home", "Favorites")

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            navItems.forEachIndexed { index, item ->
                item(
                    icon = {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = null)
                    },
                    label = {
                        Text(text = item)
                    },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index }
                )
            }
        }
    )

}
