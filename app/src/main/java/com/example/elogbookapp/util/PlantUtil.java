package com.example.elogbookapp.util;

import android.content.Context;

import com.example.elogbookapp.model.Plants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PlantUtil {
    public  Void getPlantData(Context context) {
        List<Plants> plantList=new ArrayList<>();
        File file = new File(context.getFilesDir(),"Plant");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String responce = stringBuilder.toString();

            JSONArray jArr = new JSONArray(responce);
            for (int i=0; i < jArr.length(); i++) {
                JSONObject jsonObject  = new JSONObject(jArr.getString(i));


                plantList.add(new Plants(jsonObject.getInt("plantId"),jsonObject.getString("plantName"),jsonObject.getBoolean("isactive"),jsonObject.getInt("createBy"),jsonObject.getString("createDate"),jsonObject.getInt("updateBy"),jsonObject.getString("updateDate") ));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
