package com.elnemr.floatingwindows.util

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Build
import android.provider.Settings
import android.view.View
import com.elnemr.floatingwindows.service.FloatingService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

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


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): T {
    return withContext(dispatcher) {
            apiCall.invoke()
    }
}

//suspend fun <T> safeApiCall(
//    dispatcher: CoroutineDispatcher = Dispatchers.IO,
//    apiCall: suspend () -> T
//): NetworkResult<T> {
//    return withContext(dispatcher) {
//        try {
//            NetworkResult.Success(apiCall.invoke())
//        } catch (throwable: Throwable) {
//            when (throwable) {
//                is IOException -> NetworkResult.Error("Please check your internet connection")
//                else -> {
//                    NetworkResult.Error("Something went error")
//                }
//            }
//        }
//    }
//}
