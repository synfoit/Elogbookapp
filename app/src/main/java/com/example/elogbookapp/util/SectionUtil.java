package com.example.elogbookapp.util;

import android.content.Context;

import com.example.elogbookapp.model.Section;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SectionUtil {
    public  List<Section> getSectionData(Context context) {
        List<Section> sectionList=new ArrayList<>();
        File file = new File(context.getFilesDir(),"Section");
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
            String response = stringBuilder.toString();
            JSONArray jArr = new JSONArray(response);
            for (int i=0; i < jArr.length(); i++) {
                JSONObject jsonObject  = new JSONObject(jArr.getString(i));

                sectionList.add(new Section(jsonObject.getInt("sectionId"),jsonObject.getString("sectionName"),jsonObject.getBoolean("isactive"),jsonObject.getInt("createBy"), jsonObject.getString("createDate"),jsonObject.getInt("updateBy"), jsonObject.getString("updateDate")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sectionList;
    }
}
