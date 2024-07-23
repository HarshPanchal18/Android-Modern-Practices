package com.example.modern_practices

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modern_practices.BioMetricManager.BioMetricResult
import com.example.modern_practices.ui.theme.ModernPracticesTheme

class MainActivity : AppCompatActivity() {

    private val promptManager by lazy {
        BioMetricManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ModernPracticesTheme {
                Surface {
                    val biometricResult by promptManager.promptResults.collectAsState(initial = null)
                    val enrollLauncher =
                        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
                            println("Activity result: $it")
                        }

                    LaunchedEffect(key1 = biometricResult) {
                        if (biometricResult is BioMetricResult.AuthenticationNotSet) {
                            if (Build.VERSION.SDK_INT >= 30) {
                                val enrollIntent =
                                    Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                        putExtra(
                                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                                        )
                                    }
                                enrollLauncher.launch(enrollIntent)
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        PasswordInput()

                        BiometricAuthButton()

                        biometricResult?.let { result ->
                            Text(
                                text = when (result) {
                                    is BioMetricResult.AuthenticationError -> "Authentication Error: ${result.error}"
                                    BioMetricResult.AuthenticationFailed -> "Authentication failed"
                                    BioMetricResult.AuthenticationNotSet -> "Authentication not set"
                                    BioMetricResult.AuthenticationSuccess -> "Authenticated successfully"
                                    BioMetricResult.FeatureUnavailable -> "Not supported"
                                    BioMetricResult.HardwareUnavailable -> "Unable to find hardware"
                                },
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }

                    }
                }
            }
        }

    }

    @Composable
    private fun BiometricAuthButton() {
        FloatingActionButton(
            onClick = {
                promptManager.showBioMetricPrompt(
                    title = "Authenticate",
                    description = "Verify that it's you..."
                )
            },
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.Fingerprint,
                contentDescription = "Fingerprint",
                modifier = Modifier.size(40.dp)
            )
        }
    }

    // @Preview(showBackground = true)
    @Composable
    private fun PasswordInput(modifier: Modifier = Modifier) {
        var password by remember { mutableStateOf("") }

        Column(modifier = modifier) {

            TextField(
                value = password,
                onValueChange = {
                    if (password.length < 4)
                        password = it
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(fraction = 0.6F),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        password = password.substring(0..<password.lastIndex)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Backspace,
                            contentDescription = "Backspace"
                        )
                    }
                },
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 64.sp
                ),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White
                )
            )

            Text(text = password)

            Keypads { clickedCharacter ->
                if (password.length < 4)
                    password += (clickedCharacter) // Append clicked character to password
            }

            // Text(text = "Password: $password")
        }

    }

    @Composable
    private fun Keypads(onClick: (String) -> Unit) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            items((1..9).toList() + " " + 0 + " ") { digit ->
                Button(
                    onClick = { onClick(digit.toString()) },
                    modifier = Modifier.wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    enabled = digit.toString().isNotBlank()
                ) {
                    Text(
                        text = digit.toString(),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 36.sp
                    )
                }
            }
        }
    }
}
