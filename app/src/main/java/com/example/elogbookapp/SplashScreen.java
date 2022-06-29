package com.example.elogbookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

import com.example.elogbookapp.repository.ParameterValueRepository;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;


@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    ParameterValueRepository repository;
    String storeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        repository = new ParameterValueRepository();

        new Handler().postDelayed(() -> {

            if (storeDate == null) {
                storeDate = Comman.getDateofDeleteData(SplashScreen.this, Comman.Key_Date);
                if (storeDate.length() == 0) {
                    storeDate = Comman.dateFormat.format(new Date());
                    Comman.saveDateofDeleteData(SplashScreen.this, Comman.Key_Date, storeDate);
                }//Comman.dateFormat.parse(storeDate)
                try {
                    long datedifference = Comman.dateDifference(Comman.dateFormat.parse(storeDate), Comman.dateFormat.parse(Comman.dateFormat.format(new Date())));
                    System.out.println("difference" + datedifference);
                    if (datedifference >= 7) {
                        repository.deleteFile(SplashScreen.this);
                        Comman.deleteDateData(SplashScreen.this);
                        Comman.saveDateofDeleteData(SplashScreen.this, Comman.Key_Date, Comman.dateFormat.format(new Date()));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken).isEmpty() || Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken) == null || Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken).length() == 0) {

                startSpecificActivity(SplashScreen.this, LoginScreen.class);

            } else {
                startSpecificActivity(SplashScreen.this, HomeScreen.class);
            }

        }, 2500);
    }


    public void startSpecificActivity(Context context, Class<?> otherActivityClass) {
        Intent intent = new Intent(context, otherActivityClass);
        startActivity(intent);
    }
}