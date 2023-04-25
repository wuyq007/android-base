package com.example.android.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.android.base.databinding.ActivityMainBinding;
import com.pers.libs.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SmsActivity2 extends BaseActivity {

    private ActivityMainBinding binding;

    //content://sms 所有短信
    //content://sms/outbox 发送箱中的短信
    //content://sms/inbox 收件箱中短信
    private Uri SMS_INBOX = Uri.parse("content://sms/inbox");

    private static final String URI_SMS_LOG = "content://sms/";
    private static final String URI_CALL_LOG = "content://call_log/calls";

    private boolean isCheckedPermission() {
        boolean isChecked = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
        if (isChecked) {
            isChecked = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
        }
        if (isChecked) {
            isChecked = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
        }
        return isChecked;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentLayout(binding.getRoot());
        setStartBarModule(true);

        if (!isCheckedPermission()) {
            String[] permissions = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS};
            ActivityCompat.requestPermissions(this, permissions, 333);
        } else {
            redSms();
        }

    }

    private void redSms() {
        SmsObserver smsObserver = new SmsObserver(this, smsHandler);
        getContentResolver().registerContentObserver(SMS_INBOX, true, smsObserver);
        getSmsFromPhone();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 333) {
            if (isCheckedPermission()) {
                redSms();
            } else {
                Toast.makeText(this, "未开启权限", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void getSmsFromPhone() {

        //短信手机号码 ： address
        //短信标题： subject
        //短信内容：body
        //短信发送时间戳：date
        //短信类型：type 【0：待发信息； 1：接收到信息； 2：发出】
        Uri uri = Uri.parse("content://sms/");
        Cursor cursor_sms = mContext.getContentResolver().query(uri, null, null, null, null);
        if (cursor_sms != null) {
            int count = cursor_sms.getCount();
            int columnCount = cursor_sms.getColumnCount();
            Log.e(SmsReceiver.TAG, "count = " + count + " ; columnCount = " + columnCount);
            while (cursor_sms.moveToNext()) {
                String id = cursor_sms.getString(cursor_sms.getColumnIndex("_id"));
                String subject = cursor_sms.getString(cursor_sms.getColumnIndex("subject"));
                String address = cursor_sms.getString(cursor_sms.getColumnIndex("address"));
                String body = cursor_sms.getString(cursor_sms.getColumnIndex("body"));
                String dateStr = cursor_sms.getString(cursor_sms.getColumnIndex("date"));
                String type = cursor_sms.getString(cursor_sms.getColumnIndex("type"));
                String sms_type = "0".equals(type) ? "待发信息" : ("1".equals(type) ? "接收到的信息" : "已发送的信息");

                Date date = new Date(Long.parseLong(dateStr));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                format.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
                String dateContent = format.format(date);
                Log.e(SmsReceiver.TAG, "\n" + id + "\n" + address + "\n" + subject + "\n" + body + "\n" + dateContent + "\n" + sms_type);

            }
            cursor_sms.close();
        }
//        Cursor mCursor = mContext.getContentResolver().query(SMS_INBOX, null, null, null, "date desc");
//        if (mCursor == null) {
//            return;
//        }
//
//        while (mCursor.moveToNext()){
//            int _inIndex = mCursor.getColumnIndex("_id");
//            if (_inIndex != -1) {
//                int thread_idIndex = mCursor.getColumnIndex("thread_id");
//
//                String thread_id = mCursor.getString(thread_idIndex);
//
//                int bodyIndex = mCursor.getColumnIndex("body");
//                String smsBody = mCursor.getString(bodyIndex);
//
//                int dateIndex = mCursor.getColumnIndex("date");
//                String date = mCursor.getString(dateIndex);
//
//                int addressIndex = mCursor.getColumnIndex("address");
//                String address = mCursor.getString(addressIndex);
//
//                Log.e(SmsReceiver.TAG, "thread_id：" + thread_id);
//                Log.e(SmsReceiver.TAG, "smsBody：" + smsBody);
//                Log.e(SmsReceiver.TAG, "date：" + date);
//                Log.e(SmsReceiver.TAG, "address：" + address);
//            }
//
//        }

    }

    public static Handler smsHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    private static class SmsObserver extends ContentObserver {
        private SmsActivity2 mContext;

        public SmsObserver(SmsActivity2 context, Handler handler) {
            super(handler);
            mContext = context;
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);
            Log.e(SmsReceiver.TAG, "SmsObserver onChange:" + uri);
            mContext.getSmsFromPhone();
        }

    }


}
