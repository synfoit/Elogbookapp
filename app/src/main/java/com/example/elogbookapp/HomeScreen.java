package com.example.elogbookapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.elogbookapp.adapter.DrawerAdpter;
import com.example.elogbookapp.adapter.TemplateMasterAdapater;
import com.example.elogbookapp.model.ManualDataDetail;
import com.example.elogbookapp.model.Template;
import com.example.elogbookapp.repository.ParameterValueRepository;
import com.example.elogbookapp.util.TemplateUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
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
    String uniqueID, userToken;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    TextInputEditText tv_datesection;
    TextView total_tem, fill_temp, total_section, fill_section;
    ProgressDialog pd;
    final Calendar myCalendar = Calendar.getInstance();

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
        templateList = templateUtil.getTemplateData(HomeScreen.this);
        pd = new ProgressDialog(this);
        total_tem = findViewById(R.id.tv_totaltemp);
        fill_temp = findViewById(R.id.tv_filltemp);
        total_section = findViewById(R.id.tv_totalsection);
        fill_section = findViewById(R.id.tv_fillsection);

        uniqueID = Comman.getUUID(HomeScreen.this, Comman.Key_UNIQUE_ID);
        userToken = Comman.getSavedUserData(HomeScreen.this, Comman.Key_Usertoken);
        DrawerAdpter.selectedItem = 0;
        tv_datesection = findViewById(R.id.et_date);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
        imageView = findViewById(R.id.im_sync);

        total_tem.setText("Total Template :"+templateList.size());
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();


        };
        tv_datesection.setOnClickListener(view -> new DatePickerDialog(HomeScreen.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());


        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.it_sync) {
                syncDataToServer();
            } else if (item.getItemId() == R.id.it_showdata) {
                startSpecificActivity(HomeScreen.this, ShowstoreData.class, "", "");
            } else if (item.getItemId() == R.id.edit) {
                Comman.deleteUserData(HomeScreen.this);
                Intent intent = new Intent(HomeScreen.this, LoginScreen.class);
              startActivity(intent);
            }

            return true;
        });

        // Sync Data to server with template session is completed
        imageView.setOnClickListener(view -> new SendData().execute());


    }


    @SuppressLint("StaticFieldLeak")
    private class SendData extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {
            pd.setMessage("Sync...");
            pd.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            // do above Server call here
            comman.intertnetStricNode();
            return syncDataToServer();
        }

        @Override
        protected void onPostExecute(Integer responce) {
            if (pd != null)
                pd.dismiss();
            if (!responce.equals("")) {

            } else {


            }

        }
    }

    public int syncDataToServer() {
        if (connectionDetector.isConnectingToInternet()) {
            Map<String, List<ManualDataDetail>> listMap = parameterValueRepository.getSyncData(HomeScreen.this);
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
                        List<ManualDataDetail> manualDataDetails = parameterValueRepository.getPVDataTemplateId(HomeScreen.this, Comapredate, storeTemplateId, uniqueID, userToken);

                        int sectionParamCount = 0;
                        for (int m = 0; m < template.getSectionParametersList().size(); m++)
                        {
                            sectionParamCount += template.getSectionParametersList().get(m).getParameters().size();

                        }
                        if (sectionParamCount == manualDataDetails.size())
                        {
                            parameterValueRepository.sendDataToServer(HomeScreen.this, manualDataDetails);

                            Toast.makeText(HomeScreen.this, "TemplateID" + templateId + "Date" + Comapredate + "Sync", Toast.LENGTH_SHORT).show();

                        } else
                        {
                            Toast.makeText(HomeScreen.this, "TemplateID" + templateId + "not complete" + Comapredate + "on date", Toast.LENGTH_SHORT).show();
                            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Template " + templateId + "  not complete").setContentText("Please fill data on  " + Comapredate).show();

                        }
                    }
                }
            }

            return 1;
        }
        else
        {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Internet is not connect").setContentText("Please check your internet").show();
            return 0;
        }
    }

    public void DataToServer() {

        int total_section=0;
        int entry_temp=0;
        for (int k = 0; k < templateList.size(); k++) {
            Template template = templateList.get(k);
            total_section +=template.getSectionParametersList().size();

        }

        Map<String, List<ManualDataDetail>> listMap = parameterValueRepository.getSyncData(HomeScreen.this);
        for (Map.Entry<String, List<ManualDataDetail>> entry : listMap.entrySet()) {
            entry_temp +=1;
            String[] value = entry.getKey().split(",");
            String Comapredate = value[4].substring(0, 10);
            List<ManualDataDetail> manualDatalist = entry.getValue();
            int storeTemplateId = Integer.parseInt(value[3]);
            HashMap<Integer, List<ManualDataDetail>> hashMap = new HashMap<Integer, List<ManualDataDetail>>();
            List<ManualDataDetail> manualDataDetails = parameterValueRepository.getPVDataTemplateId(HomeScreen.this, Comman.dateFormat.format(myCalendar.getTime()), storeTemplateId, uniqueID, userToken);



            int sectionParamCount = 0;
            for (int m = 0; m < manualDataDetails.size(); m++) {
                ManualDataDetail manualDataDetail=manualDataDetails.get(m);
                sectionParamCount +=1;
                //sectionParamCount += template.getSectionParametersList().get(m).getParameters().size();

            }
        }
     /*   */

    }

    public void startSpecificActivity(Context context, Class<?> otherActivityClass, String key, String value) {
        Intent intent = new Intent(context, otherActivityClass);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    private void updateLabel() {

        tv_datesection.setText(Comman.dateFormat.format(myCalendar.getTime()));
    }
}