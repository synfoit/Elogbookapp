package com.example.elogbookapp.util;

import android.content.Context;
import android.util.Log;
import com.example.elogbookapp.model.Area;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AreaUtil {

    public  Void toJSon(Context context) {
        List<Area> areaList=new ArrayList<>();
        File file = new File(context.getFilesDir(),"Area");
        FileReader fileReader ;
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
                areaList.add(new Area(jsonObject.getInt("areaId"),jsonObject.getString("areaName"),jsonObject.getBoolean("isactive"),jsonObject.getInt("createBy"),jsonObject.getString("createDate"), Objects.requireNonNull(jsonObject.getInt("updateBy")), jsonObject.getString("updateDate") ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
