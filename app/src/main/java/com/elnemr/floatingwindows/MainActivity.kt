package com.elnemr.floatingwindows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elnemr.floatingwindows.util.Constants
import com.elnemr.floatingwindows.util.startFloatingService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startFloatingService(Constants.SERVICE_START)
    }
}