package com.github.simplyblue77.appsizetest

import android.annotation.TargetApi
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.github.simplyblue77.appsizetest.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        if (!checkForPermission()) {
            startActivityForResult(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), REQ_CODE_USAGE_ACCESS)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_USAGE_ACCESS) {
            val toastMsg = if (checkForPermission()) "permission granted" else "permission denied"
            Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun checkForPermission(): Boolean {
        var appOpsManager = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        var mode = appOpsManager.checkOpNoThrow(
            "android:get_usage_stats",
            android.os.Process.myUid(),
            applicationContext.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    companion object {
        const val REQ_CODE_USAGE_ACCESS = 101
    }

}
