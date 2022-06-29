package com.example.elogbookapp.util;

import android.content.Context;

import com.example.elogbookapp.model.SectionParameters;
import com.example.elogbookapp.model.Template;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TemplateUtil {
    public   List<Template> getTemplateData(Context context) {
        List<Template> templateList=new ArrayList<>();
        File file = new File(context.getFilesDir(),"Template");
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
            int updateby=0;
            String updateDate="";
            for (int i=0; i < jArr.length(); i++) {
                JSONObject jsonObject  = new JSONObject(jArr.getString(i));


               JSONArray jsonArray=jsonObject.getJSONArray("sectionParameters");
                ArrayList<SectionParameters> sectionParametersList=new ArrayList<>();
               for (int j=0;j<jsonArray.length();j++)
               {
                   JSONObject obj = jsonArray.getJSONObject(j);
                   sectionParametersList.add(new SectionParameters(obj.getInt("sectionId"),obj.getString("sectionName"),obj.getInt("parameterId"),obj.getString("parameterName"),obj.getInt("templateId")));
               }

                templateList.add(new Template(jsonObject.getInt("templateId"),jsonObject.getString("templateName"),jsonObject.getString("parameterName"),jsonObject.getString("parameterDetail"),jsonObject.getString("plantName"),jsonObject.getString("zoneName"),jsonObject.getString("locationName"),jsonObject.getInt("parameterId"),jsonObject.getInt("plantId"),jsonObject.getInt("zoneId"),jsonObject.getInt("locationId"),jsonObject.getInt("dictionaryId"),jsonObject.getInt("createBy"), jsonObject.getString("createDate"), updateby,updateDate,jsonObject.getBoolean("isActive"),sectionParametersList ));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return templateList;
    }
}
