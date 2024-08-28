package com.example.modern_practices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.AnimatedPane
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Surface {
                CarDetailLayout()
            }
        }

    }
}

@Composable
fun CarList(modifier: Modifier = Modifier, onClick: (Car) -> Unit) {
    LazyColumn {
        items(10) {
            Text(
                text = "Car id is $it",
                modifier = Modifier.clickable { onClick(Car(it)) }
            )
        }
    }
}

@Composable
fun CarDetails(modifier: Modifier = Modifier, car: Car) {
    Text(
        text = "The car id is ${car.id}.",
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    )
}

/*
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
*/

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Preview(showBackground = true)
@Composable
fun CarDetailLayout(modifier: Modifier = Modifier) {

    val selectedCar = rememberSaveable(stateSaver = Car.Saver) { mutableStateOf(null) }
    val navigator = rememberListDetailPaneScaffoldNavigator()

    ListDetailPaneScaffold(
        scaffoldState = navigator.scaffoldState,
        listPane = {
            AnimatedPane(modifier = Modifier.width(20.dp)) {
                CarList(modifier = Modifier.fillMaxWidth()) {
                    selectedCar.value = it
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                }
            }
        }
    ) {
        AnimatedPane(modifier = Modifier) {
            selectedCar.value?.let { car ->
                CarDetails(car = car)
            }
        }
    }

}

data class Car(val id: Int) {
    companion object { // Making it saveable
        val Saver: Saver<Car?, Int> = Saver({ it?.id }, ::Car)
    }
}
