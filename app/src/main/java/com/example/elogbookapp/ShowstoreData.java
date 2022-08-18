package com.example.elogbookapp;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.elogbookapp.adapter.ShowStoreDataAdapter;
import com.example.elogbookapp.model.ManualDataDetail;
import com.example.elogbookapp.repository.ParameterValueRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShowstoreData extends AppCompatActivity {
    Comman comman;



    RecyclerView recyclerViewListView;
    ShowStoreDataAdapter ListAdapter;

    ParameterValueRepository parameterValueRepository;

    String uniqueID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showdata_recyclerview);
        Toolbar toolbar = findViewById(R.id.Mytoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);

        uniqueID = Comman.getUUID(ShowstoreData.this,Comman.Key_UNIQUE_ID);

        comman = new Comman(ShowstoreData.this);
        comman.sideBar(toolbar, ShowstoreData.this, ShowstoreData.this);
        parameterValueRepository = new ParameterValueRepository(ShowstoreData.this,Comman.Key_Usertoken);
        recyclerViewListView=findViewById(R.id.recyclerView);


        List<ManualDataDetail>  lists=new ArrayList<>();
        Map<String, List<ManualDataDetail>> listMap = parameterValueRepository.getSyncData(ShowstoreData.this);
        for (Map.Entry<String, List<ManualDataDetail>> entry : listMap.entrySet()) {
            lists.addAll(entry.getValue());
        }


        ListAdapter= new ShowStoreDataAdapter(ShowstoreData.this,lists);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewListView.setLayoutManager(manager);
        recyclerViewListView.setAdapter(ListAdapter);
        ListAdapter.notifyDataSetChanged();
    }
}
