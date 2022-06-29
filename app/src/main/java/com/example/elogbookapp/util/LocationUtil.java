package com.example.elogbookapp.util;

import android.content.Context;
import android.util.Log;

import com.example.elogbookapp.model.Location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LocationUtil {
    public  Void getLocationData(Context context) {
        List<Location> locationList=new ArrayList<>();
        File file = new File(context.getFilesDir(),"Location");
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
            Log.d("responce//",responce);
            JSONArray jArr = new JSONArray(responce);
            for (int i=0; i < jArr.length(); i++) {
                JSONObject jsonObject  = new JSONObject(jArr.getString(i));

                Log.d("name",jsonObject.getString("locationName"));

                locationList.add(new Location(jsonObject.getInt("locationId"),jsonObject.getString("locationName"),jsonObject.getBoolean("isactive"),jsonObject.getInt("createBy"),jsonObject.getString("createDate"),jsonObject.getInt("updateBy"),jsonObject.getString("updateDate")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
