package com.elnemr.floatingwindows

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.elnemr.floatingwindows.util.Constants
import com.elnemr.floatingwindows.util.drawOverOtherAppsEnabled
import com.elnemr.floatingwindows.util.startFloatingService

const val PERMISSION_REQUEST_CODE = 1

class PermissionActivity : AppCompatActivity() {

    private fun showDialog(titleText: String, messageText: String) {
        with(AlertDialog.Builder(this)) {
            title = titleText
            setMessage(messageText)
            setPositiveButton(R.string.common_ok) { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission() {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        try {
            startActivityForResult(intent, PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            showDialog(
                getString(R.string.permission_error_title),
                getString(R.string.permission_error_text)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        findViewById<Button>(R.id.btn_perms).setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                finish()
            } else {
                requestPermission()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Don't check for resultCode == Activity.RESULT_OK because the overlay activity
        // is closed with the back button and so the RESULT_CANCELLED is always received.
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (drawOverOtherAppsEnabled()) {
                // The permission has been granted.
                // Resend the last command - we have only one, so no additional logic needed.
                startFloatingService(Constants.SERVICE_ADD_NOTE)
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}