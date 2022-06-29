package com.example.elogbookapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elogbookapp.adapter.DrawerAdpter;
import com.example.elogbookapp.adapter.TemplateMasterAdapater;
import com.example.elogbookapp.model.ManualDataDetail;
import com.example.elogbookapp.model.Template;
import com.example.elogbookapp.repository.ParameterValueRepository;
import com.example.elogbookapp.util.TemplateUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TMScreen extends AppCompatActivity {
    Comman comman;

    public TextInputLayout selection, datesection;
    TextInputEditText tv_datesection;

    Button cancel_button, submit;
    static List<Template> templateList;

    @SuppressLint("SimpleDateFormat")

    final Calendar myCalendar = Calendar.getInstance();
    RecyclerView recyclerViewListView;
    TemplateMasterAdapater ListAdapter;
    TemplateUtil templateUtil;
    ParameterValueRepository parameterValueRepository;
    String uniqueID, selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_list);
        Toolbar toolbar = findViewById(R.id.Mytoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);

        comman = new Comman(TMScreen.this);
        comman.sideBar(toolbar, TMScreen.this, TMScreen.this);

        parameterValueRepository = new ParameterValueRepository();

        String selectTem = getIntent().getStringExtra("temp");

        DrawerAdpter.selectedItem = 1;

        submit = findViewById(R.id.bt_sumit);
        recyclerViewListView = findViewById(R.id.recyclerView);
        selection = findViewById(R.id.cpdTypeLayout);
        datesection = findViewById(R.id.qqet_date);
        tv_datesection = findViewById(R.id.et_date);
        cancel_button = findViewById(R.id.bt_cancel);

        selectedDate = Comman.dateFormat.format(new Date());

        tv_datesection.setText(selectedDate);

        uniqueID = Comman.getSavedUserData(TMScreen.this, Comman.Key_UNIQUE_ID);

        templateUtil = new TemplateUtil();
        templateList = templateUtil.getTemplateData(TMScreen.this);

        ListAdapter = new TemplateMasterAdapater(templateList, TMScreen.this, uniqueID, Comman.dateFormat.format(myCalendar.getTime()), selectTem);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewListView.setLayoutManager(manager);
        recyclerViewListView.setAdapter(ListAdapter);

        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();

            ListAdapter = new TemplateMasterAdapater(templateList, TMScreen.this, uniqueID, Comman.dateFormat.format(myCalendar.getTime()), selectTem);
            recyclerViewListView.setLayoutManager(manager);
            recyclerViewListView.setAdapter(ListAdapter);
        };

        tv_datesection.setOnClickListener(view -> new DatePickerDialog(TMScreen.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        cancel_button.setOnClickListener(view -> {
            Intent i = new Intent(TMScreen.this, HomeScreen.class);
            startActivity(i);
            finish();
        });

        submit.setOnClickListener(view -> saveDataOnLocal());


    }

    @SuppressLint("NotifyDataSetChanged")
    public void saveDataOnLocal(){
        List<ManualDataDetail> parametervaluelist = new ArrayList<>();

        //Get Value from edittext int and text
        for (int k = 0; k < ListAdapter.getListData().size(); k++) {
            Map<String, EditText> stringEditTextMap = ListAdapter.getListData().get(k);
            for (Map.Entry<String, EditText> entry : stringEditTextMap.entrySet()) {

                String[] idvalue = entry.getKey().split(",");
                if (!entry.getValue().getText().toString().isEmpty()) {
                    ManualDataDetail manualDataDetail = new ManualDataDetail(0, uniqueID, Comman.dateAndTimeformat.format(myCalendar.getTime()), Integer.parseInt(idvalue[0]), Integer.parseInt(idvalue[2]), Integer.parseInt(idvalue[1]), entry.getValue().getText().toString());
                    parametervaluelist.add(manualDataDetail);
                }
            }
        }
        //Get Value from Dropdown
        for (int k = 0; k < ListAdapter.getdropData().size(); k++) {
            Map<String, String> stringMap = ListAdapter.getdropData().get(k);
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {

                 //Get value of templateId,sectionId,parameterId using key
                String[] idvalue = entry.getKey().split(",");

                if (!entry.getValue().isEmpty()) {
                    ManualDataDetail manualDataDetail = new ManualDataDetail(0, uniqueID, Comman.dateAndTimeformat.format(myCalendar.getTime()), Integer.parseInt(idvalue[0]), Integer.parseInt(idvalue[2]), Integer.parseInt(idvalue[1]), entry.getValue());
                    parametervaluelist.add(manualDataDetail);
                }

            }
        }
        //Set value template vise
        for (int m = 0; m < templateList.size(); m++) {
            List<ManualDataDetail> manualDataDetails = new ArrayList<>();
            Template template = templateList.get(m);

            for (int n = 0; n < parametervaluelist.size(); n++) {
                ManualDataDetail manualDataDetail = parametervaluelist.get(n);
                if (template.getTemplateId() == manualDataDetail.getTemplateId()) {
                    manualDataDetails.add(manualDataDetail);
                }
            }

            //Save Data Local json format template vise
            if (manualDataDetails.size() != 0 && manualDataDetails.size() != 1) {
                parameterValueRepository.setParameterValue(manualDataDetails, TMScreen.this, Comman.dateFormat.format(myCalendar.getTime()), template.getTemplateId(), uniqueID);
            }
        }

        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Saved!").setContentText("Data Save Local!").show();


       ListAdapter.notifyDataSetChanged();
    }
    private void updateLabel() {

        tv_datesection.setText(Comman.dateFormat.format(myCalendar.getTime()));
    }
}
