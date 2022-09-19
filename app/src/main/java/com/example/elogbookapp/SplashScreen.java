package com.example.elogbookapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.elogbookapp.model.Template;
import com.example.elogbookapp.repository.JSONParser;
import com.example.elogbookapp.repository.ParameterValueRepository;
import com.example.elogbookapp.util.SendDataToServer;
import com.example.elogbookapp.util.TemplateUtil;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    ParameterValueRepository repository;
    JSONParser jp;
    String storeDate, updateDate;
    LoginScreen loginScreen;
    HomeScreen homeScreen;
    ConnectionDetector connectionDetector;
    private static String uniqueID = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        jp = new JSONParser();
        loginScreen = new LoginScreen();
        homeScreen = new HomeScreen();
        connectionDetector = new ConnectionDetector(SplashScreen.this);

        new Handler().postDelayed(() -> {
            Thread gfgThread = new Thread(() -> {
                if (uniqueID == null) {
                    uniqueID = Comman.getUUID(SplashScreen.this, Comman.Key_UNIQUE_ID);

                    if (uniqueID.length() == 0) {

                        startSpecificActivity(SplashScreen.this, AddDeviceId.class);

                    } else {
                        if (!Comman.getLicenseModule(SplashScreen.this)) {
                            startSpecificActivity(SplashScreen.this, LicenseScreen.class);
                        } else {
                            if (Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken).isEmpty() || Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken) == null || Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken).length() == 0) {

                                startSpecificActivity(SplashScreen.this, LoginScreen.class);

                            } else {
                                syncModule();
                                deleteLocalData();
                                startSpecificActivity(SplashScreen.this, HomeScreen.class);

                            }
                        }
                    }
                }

            });

            gfgThread.start();
        }, 2500);
    }


    public void syncModule() {
        try {
            JSONObject objectDate = jp.getSyncDate(ApiUrl.sync, Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken));
            if (updateDate == null) {
                updateDate = Comman.getUpdateDate(SplashScreen.this, Comman.Key_Update);
                if (updateDate.length() == 0) {
                    Comman.saveUpdateDate(SplashScreen.this, Comman.Key_Update, objectDate.getString("lastModifiedDate"));
                } else if (!updateDate.equals(objectDate.getString("lastModifiedDate"))) {
                    SendDataToServer st = new SendDataToServer();
                    st.syncDataToServer(SplashScreen.this, Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken), Comman.getUUID(SplashScreen.this, Comman.Key_UNIQUE_ID));
                    loginScreen.getAllMasterData();
                    Comman.deleteUpdateData(SplashScreen.this);
                    Comman.saveUpdateDate(SplashScreen.this, Comman.Key_Update, objectDate.getString("lastModifiedDate"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLocalData() {
        if (storeDate == null) {
            storeDate = Comman.getDateofDeleteData(SplashScreen.this, Comman.Key_Date);
            if (storeDate.length() == 0) {
                storeDate = Comman.dateFormat.format(new Date());
                Comman.saveDateofDeleteData(SplashScreen.this, Comman.Key_Date, storeDate);
            }
            try {
                long difference = Comman.dateDifference(Comman.dateFormat.parse(storeDate), Comman.dateFormat.parse(Comman.dateFormat.format(new Date())));

                if (difference >= 7) {
                    SendDataToServer st = new SendDataToServer();
                    st.syncDataToServer(SplashScreen.this, Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken), Comman.getUUID(SplashScreen.this, Comman.Key_UNIQUE_ID));

                    repository = new ParameterValueRepository(SplashScreen.this, Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken));
                    repository.deleteFile(SplashScreen.this);


                    Comman.deleteDateData(SplashScreen.this);
                    Comman.saveDateofDeleteData(SplashScreen.this, Comman.Key_Date, Comman.dateFormat.format(new Date()));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void startSpecificActivity(Context context, Class<?> otherActivityClass) {
        Intent intent = new Intent(context, otherActivityClass);
        startActivity(intent);
        finish();
    }


}