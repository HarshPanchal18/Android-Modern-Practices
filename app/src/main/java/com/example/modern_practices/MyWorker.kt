package com.example.modern_practices

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        showNotification(title = "Work Manager Demo", message = "Task finished successfully")
        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val channel = NotificationChannelCompat.Builder(
            "default_channel",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            setName("channel name") // Must set! Don't remove
            setDescription("channel description")
            setLightsEnabled(true)
            setLightColor(Color.RED)
            setVibrationEnabled(true)
            setVibrationPattern(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
        }.build()

        NotificationManagerCompat.from(applicationContext).createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, "default_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .build()

        with(NotificationManagerCompat.from(applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // here to request the missing permissions, and then overriding
                // public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(1, notification)
        }
    }
}
