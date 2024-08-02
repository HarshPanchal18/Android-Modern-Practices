package com.example.modern_practices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modern_practices.ui.theme.ModernPracticesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ModernPracticesTheme {
                val selectedIsCompleted by mainViewModel.isCompleted.collectAsState(initial = false)
                val isCompletedStatus = if (selectedIsCompleted) "Yes" else "No"

                // .collectAsState() will return a State<T> object. Every time there would be new value posted into the Flow, the returned State will be updated causing recomposition of every State.
                val selectedPriority by mainViewModel.priority.collectAsState(initial = Priority.Low)
                val priorityStatus = when (selectedPriority) {
                    Priority.High -> "High"
                    Priority.Medium -> "Medium"
                    Priority.Low -> "Low"
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Task Status",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )

                        Spacer(modifier = Modifier.size(16.dp))

                        Text(text = "Completed: $isCompletedStatus", fontSize = 22.sp)

                        Switch(
                            checked = selectedIsCompleted,
                            onCheckedChange = { mainViewModel.updateIsCompleted(it) })

                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth(0.7F)
                                .padding(vertical = 8.dp)
                        )

                        Text(text = "Priority: $priorityStatus", fontSize = 22.sp)

                        PriorityRow(
                            mainViewModel = mainViewModel,
                            selectedPriority = selectedPriority
                        )

                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth(0.7F)
                                .padding(vertical = 8.dp)
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun PriorityRow(
    mainViewModel: MainViewModel,
    selectedPriority: Priority,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(0.7F),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Priority.entries.forEach { priority ->
            Text(text = priority.name, overflow = TextOverflow.Ellipsis)
            RadioButton(
                selected = selectedPriority == priority,
                onClick = { mainViewModel.updatePriority(priority) },
                colors = RadioButtonDefaults.colors(selectedColor = priority.color)
            )
        }
    }
}
