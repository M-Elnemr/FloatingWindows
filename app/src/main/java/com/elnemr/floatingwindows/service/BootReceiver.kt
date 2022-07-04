package com.elnemr.floatingwindows.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.elnemr.floatingwindows.util.Constants
import com.elnemr.floatingwindows.util.startFloatingService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
//        if (intent?.action == Intent.ACTION_BOOT_COMPLETED)
            context?.startFloatingService(Constants.SERVICE_START)
    }
}