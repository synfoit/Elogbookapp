package com.example.elogbookapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class MybroadcastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(
                intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)
        )
        {
            Intent intent1=new Intent(context,ForegroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent1);
            }
        }
    }
}
