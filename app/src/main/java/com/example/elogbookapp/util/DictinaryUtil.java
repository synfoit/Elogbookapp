package com.example.elogbookapp.util;

import android.content.Context;
import android.util.Log;
import com.example.elogbookapp.model.Dictionary;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

public class DictinaryUtil {
    public  List<Dictionary> getDictionaryData(Context context) {
        List<Dictionary> dictionaryList=new ArrayList<>();
        List<String> stringList=new ArrayList<>();
        File file = new File(context.getFilesDir(),"Dictionary");
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
                dictionaryList.add(new Dictionary(jsonObject.getInt("dictionaryId"),jsonObject.getString("dictionaryName"),jsonObject.getString("dictionaryValue"),jsonObject.getInt("sortorder"),jsonObject.getBoolean("isactive"),jsonObject.getInt("createBy"), jsonObject.getString("createDate"),2,jsonObject.getString("updateDate") ));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionaryList;
    }
}
