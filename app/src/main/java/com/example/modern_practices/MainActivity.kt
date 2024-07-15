package com.example.modern_practices

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

val supabaseClient = createSupabaseClient(
    supabaseKey = "supabase_key",
    supabaseUrl = "supabase_url"
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
                Surface {
                    Column(modifier = Modifier.fillMaxSize()) {
                        GoogleSignInButton()
                        InsertButton()
                    }
                }
            }
        }
    }
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
        val googleIdOption = GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false)
            .setServerClientId("server_id")
            .setNonce(hashedNonce).build()
        val request = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(context = context, request = request)
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                supabaseClient.auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                    nonce = rawNonce
                }

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

@Composable
fun InsertButton(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Button(onClick = {
        coroutineScope.launch {
            try {
                // val userId = supabaseClient.auth.sessionManager.loadSession()?.user?.id
                noteTable.insert(mapOf("body" to "Hello from android!"))
                Toast.makeText(context, "New row inserted", Toast.LENGTH_SHORT).show()
            } catch (e: RestException) {
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }) {
        Text(text = "Insert new row")
    }
}

/*@Composable
fun DeleteNoteButton(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()

    Button(onClick = {
        coroutineScope.launch {
            val userId = supabaseClient.auth.sessionManager.loadSession()?.user?.id
            Log.i(TAG, "DeleteNoteButton: $userId")
            noteTable.delete {
                filter {
                    eq("user_id", "f401b6b6-9efb-4f22-a7a0-248f09f5f62d")
                }
            }
        }
    }) {
        Text(text = "Delete notes")
    }
}*/
