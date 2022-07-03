package com.elnemr.floatingwindows.util

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Build
import android.provider.Settings
import android.view.View
import com.elnemr.floatingwindows.FloatingService
import com.elnemr.floatingwindows.PermissionActivity

fun Context.startFloatingService(action: String = "") {

    val intent = Intent(this, FloatingService::class.java)
    if (action.isNotBlank()) {
        intent.action = action
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.startForegroundService(intent)
    } else {
        this.startService(intent)
    }

}

fun Context.drawOverOtherAppsEnabled(): Boolean {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        true
    } else {
        Settings.canDrawOverlays(this)
    }
}

fun Context.startPermissionActivity() {
    startActivity(
        Intent(this, PermissionActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    )
}

fun View.registerDraggableTouchListener(
    initialPosition: () -> Point,
    positionListener: (x: Int, y: Int) -> Unit
) {
    DraggableTouchListener(context, this, initialPosition, positionListener)
}