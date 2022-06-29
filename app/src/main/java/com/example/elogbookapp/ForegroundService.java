package com.example.elogbookapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.elogbookapp.repository.ParameterValueRepository;

import java.util.Calendar;
import java.util.Date;

public class ForegroundService  extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    ParameterValueRepository parameterValueRepository;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {

        parameterValueRepository = new ParameterValueRepository();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e("Services", "Services is running..........");
                    try {

                        Date todayDate = Calendar.getInstance().getTime();
                        String day = Comman.dayFormat.format(todayDate);
                        String time=Comman.timeFormat.format(todayDate);
                        // Delete Local File Data Every 7 day

                        if (day.equalsIgnoreCase("Sunday")) {

                            Log.e("Services","Services is running..!11111........");
                            if(time.equalsIgnoreCase("00:00:10"))
                            {
                            Log.e("Services","Services is running..!111112222........");
                            parameterValueRepository.deleteFile(ForegroundService.this);

                            }

                        }
                        Thread.sleep(300000);
                    } catch (Exception e) {

                    }
                }
            }
        }).start();
        final String CHANNELID="Foreground Service ID";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel=new NotificationChannel(
                        CHANNELID,
                        CHANNELID,
                        NotificationManager.IMPORTANCE_LOW
                );
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);

                Notification.Builder notification=new Notification.Builder(ForegroundService.this,CHANNELID)
                        .setContentText("Service Running")
                        .setContentTitle("Service enable")
                        .setSmallIcon(R.drawable.icons_home);

                startForeground(1001,notification.build());
            }
        }
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
