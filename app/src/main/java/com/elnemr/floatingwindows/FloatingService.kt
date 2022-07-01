package com.elnemr.floatingwindows

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.elnemr.floatingwindows.util.Constants
import com.elnemr.floatingwindows.util.drawOverOtherAppsEnabled
import com.elnemr.floatingwindows.util.startPermissionActivity

class FloatingService : Service() {

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            Log.d("TAG", "onStartCommand: ${it.action}")

            when (it.action) {
                Constants.SERVICE_EXIT -> {
                    stopService()
                    START_NOT_STICKY
                }
                Constants.SERVICE_ADD_NOTE -> {
                    if (!drawOverOtherAppsEnabled()) {
                        startPermissionActivity()
                    } else {
                        Toast.makeText(
                            this,
                            "Floating window to be added in the next lessons.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                Constants.SERVICE_START -> startFloatingService()
                else -> {
                }
            }
        }

        return START_STICKY
    }


    private fun stopService() {
        stopForeground(true)
        stopSelf()
    }

    private fun startFloatingService() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val exitIntent = Intent(this, FloatingService::class.java).apply {
            action = Constants.SERVICE_EXIT
        }

        val exitPendingIntent =
            PendingIntent.getService(this, Constants.CODE_EXIT_INTENT, exitIntent, 0)

        val noteIntent = Intent(this, FloatingService::class.java).apply {
            action = Constants.SERVICE_ADD_NOTE
        }
        val notePendingIntent =
            PendingIntent.getService(this, Constants.CODE_NOTE_INTENT, noteIntent, 0)

        val notification = NotificationCompat.Builder(this, Constants.CHANNEL_ID).apply {
            setContentTitle("Floating Service")
            setSmallIcon(R.drawable.ic_note)
            setTicker(null)
            setAutoCancel(false)
            setOngoing(true)
            setWhen(System.currentTimeMillis())
            setContentIntent(notePendingIntent)
            priority = Notification.PRIORITY_DEFAULT

            addAction(R.drawable.ic_close, "EXIT", exitPendingIntent)
        }

        startForeground(Constants.NOTIFICATION_ID, notification.build())

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        NotificationChannel(
            Constants.CHANNEL_ID,
            Constants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            enableLights(false)
            setShowBadge(false)
            enableVibration(false)
            setSound(null, null)
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(this)
        }
    }
}