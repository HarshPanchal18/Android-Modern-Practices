package com.example.modern_practices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.modern_practices.ui.theme.ModernPracticesTheme
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

val supabaseClient = createSupabaseClient(
    supabaseKey = "supabase_key",
    supabaseUrl = "supabase_url"
) {
    install(Postgrest)
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ModernPracticesTheme {
                Surface {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text("Hello World")
                        val notes = runBlocking {
                            val results = supabaseClient.from(table = "note")
                                .select()
                                .decodeList<Note>()
                            results.toMutableList()
                        }
                        NoteList(notes = notes)
                    }
                }
            }
        }
    }
}

@Serializable
data class Note(val id: Int, val body: String)

@Composable
fun NoteList(notes: List<Note>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(notes) { note ->
            ListItem(headlineContent = {
                Text(text = "${note.id} : ${note.body}")
            })
        }
    }
}
