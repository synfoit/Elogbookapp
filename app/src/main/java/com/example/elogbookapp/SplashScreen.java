package com.example.elogbookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.example.elogbookapp.model.ManualDataDetail;
import com.example.elogbookapp.model.Template;
import com.example.elogbookapp.repository.JSONParser;
import com.example.elogbookapp.repository.ParameterValueRepository;
import com.example.elogbookapp.util.TemplateUtil;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;


@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    ParameterValueRepository repository;
    JSONParser jp;
    String storeDate, updateDate;
    LoginScreen loginScreen;
    HomeScreen homeScreen;
    ConnectionDetector connectionDetector;
    ParameterValueRepository parameterValueRepository;
    TemplateUtil templateUtil;
    List<Template> templateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        jp = new JSONParser();
        loginScreen=new LoginScreen();
        homeScreen=new HomeScreen();
        templateUtil = new TemplateUtil();
        templateList = templateUtil.getTemplateData(SplashScreen.this);
        parameterValueRepository = new ParameterValueRepository(SplashScreen.this, Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken));
        connectionDetector = new ConnectionDetector(SplashScreen.this);
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
                        repository = new ParameterValueRepository(SplashScreen.this, Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken));
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
                Thread gfgThread = new Thread(() -> {
                    try {

                        String decodedtext = TrippleDes.Decrypt("BBeWWGyDF8SzUY198TA3bS3w9XOAux2jRkQldKIIUT8HjQRCBcKCv4ItBO25V8YJSTMYc5parjyiiukb2AGAWmfgyMnziDzZpy5yb53APxbS+2GJ63SU0+t7G07we2u2HUfEP0zu8MR1U1BrC6lEzHmkFyNjGbkxWVHrW1mDJ34=","s5u8x/A?D(G+KbPe");
                        System.out.println(decodedtext);

                       // JSONObject objectDate = jp.getSyncDate(ApiUrl.sync, Comman.getSavedUserData(SplashScreen.this, Comman.Key_Usertoken));

                      /*  if(updateDate==null) {
                            updateDate= Comman.getUpdateDate(SplashScreen.this,Comman.Key_Update);
                            if(updateDate.length()==0){
                                Comman.saveUpdateDate(SplashScreen.this, Comman.Key_Update, objectDate.getString("lastModifiedDate"));
                                                            }
                            else if(!updateDate.equals(objectDate.getString("lastModifiedDate"))) {
                                homeScreen.syncDataToServer();
                                loginScreen.getAllMasterData();
                                Comman.deleteUpdateData(SplashScreen.this);
                                Comman.saveUpdateDate(SplashScreen.this, Comman.Key_Update, objectDate.getString("lastModifiedDate"));

                            }
                        }*/


                        startSpecificActivity(SplashScreen.this, HomeScreen.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                gfgThread.start();
            }

        }, 2500);
    }

    public void syncDataToServer() {
        if (connectionDetector.isConnectingToInternet()) {
            Map<String, List<ManualDataDetail>> listMap = parameterValueRepository.getSyncData(SplashScreen.this);
            for (Map.Entry<String, List<ManualDataDetail>> entry : listMap.entrySet())
            {
                String[] value = entry.getKey().split(",");
                String Comapredate = value[4].substring(0, 10);


                List<ManualDataDetail> manualDatalist = entry.getValue();

                int storeTemplateId = Integer.parseInt(value[3]);


                for (int k = 0; k < templateList.size(); k++)
                {

                    Template template = templateList.get(k);
                    int templateId = template.getTemplateId();

                    if (storeTemplateId == templateId)
                    {
                        List<ManualDataDetail> manualDataDetails = parameterValueRepository.getPVDataTemplateId(SplashScreen.this, Comapredate, storeTemplateId, Comman.getUUID(SplashScreen.this,Comman.Key_UNIQUE_ID), Comman.getUUID(SplashScreen.this,Comman.Key_Usertoken));

                        int sectionParamCount = 0;
                        for (int m = 0; m < template.getSectionParametersList().size(); m++)
                        {
                            sectionParamCount += template.getSectionParametersList().get(m).getParameters().size();

                        }
                        if (sectionParamCount == manualDataDetails.size())
                        {
                            parameterValueRepository.sendDataToServer(SplashScreen.this, manualDataDetails);

                            Toast.makeText(SplashScreen.this, "TemplateID" + templateId + "Date" + Comapredate + "Sync", Toast.LENGTH_SHORT).show();

                        } else
                        {
                            Toast.makeText(SplashScreen.this, "TemplateID" + templateId + "not complete" + Comapredate + "on date", Toast.LENGTH_SHORT).show();
                            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Template " + templateId + "  not complete").setContentText("Please fill data on  " + Comapredate).show();

                        }
                    }
                }
            }
        }
        else
        {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Internet is not connect").setContentText("Please check your internet").show();
        }
    }
    public void startSpecificActivity(Context context, Class<?> otherActivityClass) {
        Intent intent = new Intent(context, otherActivityClass);
        startActivity(intent);
        finish();
    }
}