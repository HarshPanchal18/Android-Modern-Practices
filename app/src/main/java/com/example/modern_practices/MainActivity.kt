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
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.modern_practices.ui.theme.BioMetricManager
import com.example.modern_practices.ui.theme.BioMetricManager.BioMetricResult
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
                        Button(onClick = {
                            promptManager.showBioMetricPrompt(
                                title = "Authenticate",
                                description = "Verify that it's you..."
                            )
                        }) {
                            Text(text = "Authenticate")
                        }

                        biometricResult?.let { result ->
                            Text(
                                text = when (result) {
                                    is BioMetricResult.AuthenticationError -> "Authentication Error: ${result.error}"
                                    BioMetricResult.AuthenticationFailed -> "Authentication failed"
                                    BioMetricResult.AuthenticationNotSet -> "Authentication not set"
                                    BioMetricResult.AuthenticationSuccess -> "Authenticated successfully"
                                    BioMetricResult.FeatureUnavailable -> "Not supported"
                                    BioMetricResult.HardwareUnavailable -> "Unable to find hardware"
                                }
                            )
                        }

                    }
                }
            }
        }

    }
}
