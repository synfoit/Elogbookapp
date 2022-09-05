package com.example.elogbookapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.elogbookapp.adapter.DrawerAdpter;
import com.example.elogbookapp.model.ManualDataDetail;
import com.example.elogbookapp.model.Template;
import com.example.elogbookapp.repository.JSONParser;
import com.example.elogbookapp.repository.ParameterValueRepository;
import com.example.elogbookapp.util.SendDataToServer;
import com.example.elogbookapp.util.TemplateUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeScreen extends AppCompatActivity {
    Comman comman;
    JSONParser jsonParser;
    ParameterValueRepository parameterValueRepository;
    ConnectionDetector connectionDetector;
    TemplateUtil templateUtil;
    List<Template> templateList;
    String uniqueID, userToken;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    TextInputEditText tv_date_section;
    TextView total_tem, fill_temp, tv_total_section, fill_section, licenceType, expireDate;
    ProgressDialog pd;
    String licenseType = "";
    long licenseDay = 0;
    final Calendar myCalendar = Calendar.getInstance();

    @SuppressLint("SetTextI18n")
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

        parameterValueRepository = new ParameterValueRepository(HomeScreen.this, Comman.getSavedUserData(HomeScreen.this, Comman.Key_Usertoken));
        connectionDetector = new ConnectionDetector(HomeScreen.this);
        templateUtil = new TemplateUtil();
        jsonParser = new JSONParser();
        templateList = templateUtil.getTemplateData(HomeScreen.this);

        pd = new ProgressDialog(this);
        total_tem = findViewById(R.id.tv_totaltemp);
        fill_temp = findViewById(R.id.tv_filltemp);
        tv_total_section = findViewById(R.id.tv_totalsection);
        fill_section = findViewById(R.id.tv_fillsection);
        licenceType = findViewById(R.id.licenseType);
        expireDate = findViewById(R.id.expire);
        tv_date_section = findViewById(R.id.et_date);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);


        @SuppressLint("SetTextI18n") Thread gfgThread = new Thread(() -> {


            try {
                String lancesValue = jsonParser.getLincesData(ApiUrl.license, Comman.getUUID(HomeScreen.this, Comman.Key_UNIQUE_ID));
                JSONObject object = new JSONObject(lancesValue);

                String encryptedValue = object.getString("licenseKey");
                String decorated = TrippleDes.Decrypt(encryptedValue, Comman.Key_license);
                System.out.println(decorated);
                JSONObject encryptData = new JSONObject(decorated);
                licenseType = encryptData.getString("SubscriptionType");

                Date EndDate = Comman.dateFormat.parse(encryptData.getString("EndDate"));
                Date CurrentDate = new Date();
                licenseDay = Comman.dateDifference(CurrentDate, EndDate);
                licenceType.setText("License Type:  " + licenseType);
                expireDate.setText("License will expire in " + licenseDay + " days");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        gfgThread.start();

        uniqueID = Comman.getUUID(HomeScreen.this, Comman.Key_UNIQUE_ID);
        userToken = Comman.getSavedUserData(HomeScreen.this, Comman.Key_Usertoken);
        DrawerAdpter.selectedItem = 0;
        updateLabel();
        DataToServer();

        total_tem.setText(" /" + templateList.size());

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
            DataToServer();
        };

        tv_date_section.setOnClickListener(view -> new DatePickerDialog(HomeScreen.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());


        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.it_sync) {
                new SendData().execute();
            } else if (item.getItemId() == R.id.it_showdata) {
                startSpecificActivity(HomeScreen.this, ShowstoreData.class, "", "");
            } else if (item.getItemId() == R.id.edit) {
                Comman.deleteUserData(HomeScreen.this);
                Intent intent = new Intent(HomeScreen.this, LoginScreen.class);
                startActivity(intent);
            }

            return true;
        });


    }


    @SuppressLint("StaticFieldLeak")
    private class SendData extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            pd.setMessage("Sync...");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            // do above Server call here
            comman.intertnetStricNode();
            if (connectionDetector.isConnectingToInternet()) {
                SendDataToServer sd = new SendDataToServer();
                return sd.syncDataToServer(HomeScreen.this, userToken, uniqueID);
            } else {
                return "not_connect";
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (pd != null)
                pd.dismiss();
            if (response.equalsIgnoreCase("not_connect")) {
                new SweetAlertDialog(HomeScreen.this, SweetAlertDialog.ERROR_TYPE).setTitleText("Internet is not connect").setContentText("Please check your internet").show();
            }
        }
    }


    @SuppressLint("SetTextI18n")
    public void DataToServer() {

        int total_section = 0;
        int entry_temp = 0;
        int sectionParamCount = 0;


        for (int k = 0; k < templateList.size(); k++) {
            Template template = templateList.get(k);
            int templateId = template.getTemplateId();
            int total_section_temp = template.getSectionParametersList().size();
            total_section += template.getSectionParametersList().size();
            List<ManualDataDetail> manualDataDetails = parameterValueRepository.getPVDataTemplateId(HomeScreen.this, Comman.dateFormat.format(myCalendar.getTime()), templateId, uniqueID, userToken);

            int sectionParamCount1 = 0;
            int tempCount = 0;
            for (int m = 0; m < template.getSectionParametersList().size(); m++) {
                sectionParamCount1 += template.getSectionParametersList().get(m).getParameters().size();

            }
            if (sectionParamCount1 == manualDataDetails.size()) {
                sectionParamCount++;
                tempCount++;
            }
            if (total_section_temp == tempCount) {
                entry_temp++;
            }


        }
        tv_total_section.setText(" /" + total_section);
        fill_temp.setText("" + entry_temp);
        fill_section.setText(" " + sectionParamCount);

    }

    public void startSpecificActivity(Context context, Class<?> otherActivityClass, String key, String value) {
        Intent intent = new Intent(context, otherActivityClass);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    private void updateLabel() {

        tv_date_section.setText(Comman.dateFormat.format(myCalendar.getTime()));
    }

    @SuppressLint("SetTextI18n")
    public boolean getLicenseModule() {
        try {
            String lancesValue = jsonParser.getLincesData(ApiUrl.license, Comman.getUUID(HomeScreen.this, Comman.Key_UNIQUE_ID));
            JSONObject object = new JSONObject(lancesValue);

            String encryptedValue = object.getString("licenseKey");
            String decorated = TrippleDes.Decrypt(encryptedValue, Comman.Key_license);
            System.out.println(decorated);
            JSONObject encryptData = new JSONObject(decorated);
            licenseType = encryptData.getString("SubscriptionType");
            Date StarDate = Comman.dateFormat.parse(encryptData.getString("StartDate"));
            Date EndDate = Comman.dateFormat.parse(encryptData.getString("EndDate"));
            Date CurrentDate = new Date();
            licenseDay = Comman.dateDifference(CurrentDate, EndDate);
            licenceType.setText("License Type:" + licenseType);
            expireDate.setText("License will expire in " + licenseDay + " days");
            return !CurrentDate.before(StarDate) || !CurrentDate.after(EndDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}