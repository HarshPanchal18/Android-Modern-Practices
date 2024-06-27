package com.example.modern_practices

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.modern_practices.ui.theme.ModernPracticesTheme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.security.MessageDigest
import java.util.UUID

val supabaseClient = createSupabaseClient(
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndqeGl0am1oc2dqdHFiY3NqdWpsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTkyMTEwMzMsImV4cCI6MjAzNDc4NzAzM30.-IpvaP5e_1D-Tfp3t1sk3Os7E2YcvhuYtOYMz2GPdE8",
    supabaseUrl = "https://wjxitjmhsgjtqbcsjujl.supabase.co"
) {
    install(Postgrest)
    install(Auth)
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

                        /*Button(onClick = {
                            coroutineScope.launch {
                                supabaseClient.auth.signInWith(Google)
                            }
                        }) {
                            Text(text = "Start with Google.")
                        }*/

                        GoogleSignInButton()
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

@Composable
fun GoogleSignInButton(modifier: Modifier = Modifier) {
    // https://support.google.com/cloud/answer/6158849#installedapplications&android&zippy=%2Cnative-applications%2Candroid
    // https://developers.google.com/android/guides/client-auth

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val onClick: () -> Unit = {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val digest = messageDigest.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

        val credentialManager = CredentialManager.create(context)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("455330790303-655r4je8ap9v1e57te6baa5t1psh0tci.apps.googleusercontent.com")
            .setNonce(hashedNonce)
            .build()
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(context = context, request = request)
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                Log.i(TAG, "GoogleSignInButton: $googleIdToken")
                Toast.makeText(context, "You're signed in!", Toast.LENGTH_SHORT).show()
            } catch (e: GetCredentialException) {
                // Handle GetCredentialException thrown by `credentialManager.getCredential()`
                Log.e(TAG, "GoogleSignInButton: ${e.localizedMessage}")
            } catch (e: GoogleIdTokenParsingException) {
                // Handle GoogleIdTokenParsingException thrown by `GoogleIdTokenCredential.createFrom()`
                Log.e(TAG, "GoogleSignInButton: ${e.localizedMessage}")
            } catch (e: RestException) {
                // Handle RestException thrown by Supabase
                Log.e(TAG, "GoogleSignInButton: ${e.localizedMessage}")
            } catch (e: Exception) {
                // Handle unknown exceptions
                Log.e(TAG, "GoogleSignInButton: ${e.localizedMessage}")
            }
        }
    }

    Button(onClick = { onClick() }) {
        Text(text = "Sign in with Google")
    }
}
