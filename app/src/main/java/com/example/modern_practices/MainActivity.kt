package com.example.modern_practices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.modern_practices.ui.theme.ModernPracticesTheme
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

val supabaseClient = createSupabaseClient(
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndqeGl0am1oc2dqdHFiY3NqdWpsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTkyMTEwMzMsImV4cCI6MjAzNDc4NzAzM30.-IpvaP5e_1D-Tfp3t1sk3Os7E2YcvhuYtOYMz2GPdE8",
    supabaseUrl = "https://wjxitjmhsgjtqbcsjujl.supabase.co"
) {
    install(Postgrest)
}

val noteTable = supabaseClient.from(table = "note")

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ModernPracticesTheme {
                val coroutineScope = rememberCoroutineScope()
                val notes = remember { mutableStateListOf<Note>() }

                Surface {

                    LaunchedEffect(key1 = Unit) {
                        notes.addAll(
                            noteTable.select().decodeList()
                        )
                    }

                    val newId = notes.size + 1

                    Column(modifier = Modifier.fillMaxSize()) {
                        NoteList(notes = notes)

                        FloatingActionButton(
                            onClick = {
                                coroutineScope.launch {
                                    val newNote = Note(id = newId, body = "note3")
                                    insertNote(newNote)
                                    notes.add(newNote)
                                }
                            }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add note")
                        }
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

suspend fun insertNote(note: Note) {
    noteTable.insert(note)
}
