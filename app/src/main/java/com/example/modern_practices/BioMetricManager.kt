package com.example.modern_practices

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
import androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
import androidx.biometric.BiometricManager.from
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class BioMetricManager(private val activity: AppCompatActivity) {

    private val resultChannel = Channel<BioMetricResult>()
    val promptResults = resultChannel.receiveAsFlow()

    fun showBioMetricPrompt(title: String, description: String) {
        val manager = from(activity)
        val authenticators =
            if (Build.VERSION.SDK_INT >= 30)
                Authenticators.BIOMETRIC_STRONG or Authenticators.DEVICE_CREDENTIAL
            else Authenticators.BIOMETRIC_STRONG

        val promptInfo = PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setAllowedAuthenticators(authenticators)
            .setConfirmationRequired(false)

        if (Build.VERSION.SDK_INT < 30)
            promptInfo.setNegativeButtonText("Cancel")

        when (manager.canAuthenticate(authenticators)) {
            BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                resultChannel.trySend(BioMetricResult.HardwareUnavailable)
                return
            }

            BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultChannel.trySend(BioMetricResult.AuthenticationNotSet)
                return
            }

            BIOMETRIC_ERROR_NO_HARDWARE -> {
                resultChannel.trySend(BioMetricResult.FeatureUnavailable)
                return
            }

            else -> Unit

        }

        val biometricPrompt =
            BiometricPrompt(activity, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    resultChannel.trySend(BioMetricResult.AuthenticationError(errString.toString()))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    resultChannel.trySend(BioMetricResult.AuthenticationSuccess)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    resultChannel.trySend(BioMetricResult.AuthenticationFailed)
                }
            })

        biometricPrompt.authenticate(promptInfo.build())
    }

    sealed interface BioMetricResult {
        data object HardwareUnavailable : BioMetricResult
        data object FeatureUnavailable : BioMetricResult
        data class AuthenticationError(val error: String) : BioMetricResult
        data object AuthenticationFailed : BioMetricResult
        data object AuthenticationSuccess : BioMetricResult
        data object AuthenticationNotSet : BioMetricResult
    }
}
