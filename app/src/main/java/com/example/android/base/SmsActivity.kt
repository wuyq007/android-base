package com.example.android.base

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.android.base.databinding.ActivityMainBinding
import com.pers.libs.base.BaseActivity


class SmsActivity : BaseActivity() {

    companion object {
        private const val PERMISSIONS_REQUEST_READ_SMS = 123

        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.ANSWER_PHONE_CALLS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_CONTACTS
        )
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentLayout(binding.root)
        setStartBarModule(true)
        // 检查权限
        if (allPermissionsGranted()) {
            Log.e(SmsReceiver.TAG, "都有权限")
            readSms();
        } else {
            Log.e(SmsReceiver.TAG, "请求权限")
            // 如果没有权限，则请求权限
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_READ_SMS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_SMS) {
            Log.e(SmsReceiver.TAG, "请求权限返回")
            if (allPermissionsGranted()) {
                readSms();
            } else {
                Toast.makeText(this, "未开启权限", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private var smsReceiver: SmsReceiver? = null
    private fun readSms() {
        // 创建 SmsReceiver 实例
        smsReceiver = SmsReceiver()
        // 注册 SmsReceiver
        val filter = IntentFilter()
        filter.addAction(SmsReceiver.SMS_RECEIVED_ACTION)
        registerReceiver(smsReceiver, filter)
        Log.e(SmsReceiver.TAG, "注册广播")
    }

    override fun onDestroy() {
        super.onDestroy()
        // 取消注册 SmsReceiver
        smsReceiver?.let { unregisterReceiver(it) }
    }

}