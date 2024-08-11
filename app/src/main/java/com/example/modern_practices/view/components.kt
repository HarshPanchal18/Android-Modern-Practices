package com.example.modern_practices.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.modern_practices.intent.CounterIntent
import com.example.modern_practices.model.CounterState
import com.example.modern_practices.viewmodel.CounterViewModel

@Composable
fun Counter(state: CounterState?, onIntentReceived: (CounterIntent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(onClick = { onIntentReceived(CounterIntent.Increment) }) {
                Text(text = "Increment")
            }
            Text(text = "${state?.count} ")
            Button(onClick = { onIntentReceived(CounterIntent.Decrement) }) {
                Text(text = "Decrement")
            }
        }
    }
}

@Composable
fun CounterScreen(viewModel: CounterViewModel) {
    val state by viewModel.state.observeAsState()

    Counter(state = state) { intent ->
        viewModel.processIntent(intent) // send a command (Intent) to increase the count.
    }
}
