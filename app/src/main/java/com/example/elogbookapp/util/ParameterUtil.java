package com.example.elogbookapp.util;

import android.content.Context;

import com.example.elogbookapp.model.Parameter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ParameterUtil {

    public  List<Parameter> getParameterData(Context context) {
        List<Parameter> parameterList=new ArrayList<>();
        File file = new File(context.getFilesDir(),"Parameter");
        FileReader fileReader;
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

            int updateby=0;
            String updateDate="";
            JSONArray jArr = new JSONArray(responce);
            for (int i=0; i < jArr.length(); i++) {
                JSONObject jsonObject  = new JSONObject(jArr.getString(i));
                parameterList.add(new Parameter(jsonObject.getInt("parameterId"),jsonObject.getString("parameterName"),jsonObject.getString("parameterDisplayName"),jsonObject.getInt("upperLimit"),jsonObject.getInt("lowerLimit"),jsonObject.getInt("toRange"),jsonObject.getString("parameterType"),jsonObject.getString("parameterFieldType"),jsonObject.getInt("dictionaryId")));

                //parameterList.add(new Parameter(jsonObject.getInt("parameterId"),jsonObject.getString("parameterName"),jsonObject.getString("parameterDisplayName"),jsonObject.getInt("upperLimit"),jsonObject.getInt("lowerLimit"),jsonObject.getInt("toRange"),jsonObject.getInt("unitID"),jsonObject.getString("parameterType"),jsonObject.getString("parameterFieldType"),jsonObject.getInt("createBy"), jsonObject.getString("createDate"), updateby,updateDate,jsonObject.getBoolean("isactive"),jsonObject.getInt("dictionaryId"),jsonObject.getString("unitName"),jsonObject.getString("dictionaryName")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parameterList;
    }

}
