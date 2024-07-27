package com.example.modern_practices

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.example.modern_practices.ui.theme.ModernPracticesTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStore = createDataStore("settings")

        setContent {
            ModernPracticesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var key by remember { mutableStateOf("") }
                        var value by remember { mutableStateOf("") }

                        TextField(
                            value = key,
                            onValueChange = { key = it },
                            placeholder = { Text("key") })

                        TextField(
                            value = value,
                            onValueChange = { value = it },
                            placeholder = { Text("value") })

                        Row {
                            Button(onClick = {
                                lifecycleScope.launch { save(key, value) }
                            }) { Text(text = "Save") }

                            Button(onClick = {
                                lifecycleScope.launch { value = read(key) ?: "No value found" }
                            }) { Text(text = "Read") }
                        }

                    }
                }
            }
        }
    }

    private suspend fun save(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    private suspend fun read(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }
}
