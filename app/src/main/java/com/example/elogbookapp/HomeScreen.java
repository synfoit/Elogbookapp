package com.example.elogbookapp;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import com.example.elogbookapp.adapter.DrawerAdpter;
import com.example.elogbookapp.model.ManualDataDetail;
import com.example.elogbookapp.model.Template;
import com.example.elogbookapp.repository.ParameterValueRepository;
import com.example.elogbookapp.util.TemplateUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeScreen extends AppCompatActivity {
    Comman comman;
    ImageView imageView, userimage;
    ParameterValueRepository parameterValueRepository;
    ConnectionDetector connectionDetector;
    TemplateUtil templateUtil;
    List<Template> templateList;
    String uniqueID;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    LinearLayout temp1, temp2, temp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.Mytoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);

        comman = new Comman(HomeScreen.this);
        comman.sideBar(toolbar, HomeScreen.this, HomeScreen.this);

        parameterValueRepository = new ParameterValueRepository();
        connectionDetector = new ConnectionDetector(HomeScreen.this);

        templateUtil = new TemplateUtil();
        templateList = templateUtil.getTemplateData(HomeScreen.this);

        uniqueID = Comman.getSavedUserData(HomeScreen.this, "Android_DeviceUniqueID");
        DrawerAdpter.selectedItem = 0;

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
        imageView = findViewById(R.id.im_sync);
        userimage = findViewById(R.id.tv_username);

        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        temp1 = findViewById(R.id.temp1);
        temp2 = findViewById(R.id.temp2);
        temp3 = findViewById(R.id.temp3);

        temp3.setOnClickListener(view -> startSpecificActivity(HomeScreen.this, TMScreen.class, "temp", "temp3"));
        temp1.setOnClickListener(view -> startSpecificActivity(HomeScreen.this, TMScreen.class, "temp", "temp1"));
        temp2.setOnClickListener(view -> startSpecificActivity(HomeScreen.this, TMScreen.class, "temp", "temp2"));
        fab.setOnClickListener(view -> startSpecificActivity(HomeScreen.this, TMScreen.class, "temp", "temp0"));

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.it_sync) {
                syncDataToServer();
            } else if (item.getItemId() == R.id.it_showdata) {
                startSpecificActivity(HomeScreen.this, ShowstoreData.class, "", "");
            } else if (item.getItemId() == R.id.edit) {
                startSpecificActivity(HomeScreen.this, TMScreen.class, "temp", "temp0");
            }

            return true;
        });





    /*  if(!foregroundServicesRunning()) {
            Intent serviceIntent = new Intent(this, ForegroundService.class);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            }
        }*/


        // Sync Data to server with template session is completed
        imageView.setOnClickListener(view -> syncDataToServer());


    }


    public void syncDataToServer() {
        if (connectionDetector.isConnectingToInternet()) {
            Map<String, List<ManualDataDetail>> listMap = parameterValueRepository.getSyncData(HomeScreen.this);
            for (Map.Entry<String, List<ManualDataDetail>> entry : listMap.entrySet()) {

                System.out.println("go");
                String[] value = entry.getKey().split(",");
                String Comapredate = value[4].substring(0, 10);
                System.out.println("go"+Comapredate);

                List<ManualDataDetail> manualDatalist=entry.getValue();
                System.out.println("gopp"+manualDatalist.size());
                 int storeTemplateId= Integer.parseInt(value[3]);



                for (int k = 0; k < templateList.size(); k++) {

                    Template template = templateList.get(k);
                    int templateId = template.getTemplateId();

                    if(storeTemplateId==templateId) {
                        List<ManualDataDetail> manualDataDetails = parameterValueRepository.getPVDataTemplateId(HomeScreen.this, Comapredate, storeTemplateId, uniqueID);

                        if (template.getSectionParametersList().size() == manualDataDetails.size()) {

                            parameterValueRepository.sendDataToServer(HomeScreen.this, manualDataDetails);
                        } else {
                            Toast.makeText(HomeScreen.this, "TemplateID" + templateId + "not complete" + Comapredate + "on date", Toast.LENGTH_SHORT).show();
                            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Template " + templateId + "  not complete").setContentText("Please fill data on  "+Comapredate).show();

                        }
                    }

                }


            }

        } else {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Internet is not connect").setContentText("Please check your internet").show();

        }
    }

    public void startSpecificActivity(Context context, Class<?> otherActivityClass, String key, String value) {
        Intent intent = new Intent(context, otherActivityClass);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    public boolean foregroundServicesRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (ForegroundService.class.getName().equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }

        return false;
    }
}