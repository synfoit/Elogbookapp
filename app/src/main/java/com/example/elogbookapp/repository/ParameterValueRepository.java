package com.example.elogbookapp.repository;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.example.elogbookapp.ApiUrl;
import com.example.elogbookapp.ConnectionDetector;
import com.example.elogbookapp.model.ManualDataDetail;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterValueRepository {
    JSONParser jsonParser = new JSONParser();
    String userToken;

    Context context;
    String jsonObjectName = "manualDataDetailRequests";


    public ParameterValueRepository(Context context, String userToken) {
        this.context = context;
        this.userToken = userToken;

    }

    public void setParameterValue(List<ManualDataDetail> manualDataDetails, Context context, String date, int templateId, String androidId, String userToken) {


        Thread gfgThread = new Thread(() -> {
            try {
                String str = new Gson().toJson(manualDataDetails);
                System.out.println("Inertvalue" + str + userToken + androidId);
                Log.d("token", userToken);
                File file = new File(context.getFilesDir().toString() + "/Datavalue");
                file.mkdirs();
                File outputFile = new File(file, "ParameterValue," + androidId + "," + userToken + "," + templateId + "," + date + ".json");
                FileReader fileReader;
                if (outputFile.exists()) {
                    System.out.println("exitsfile");
                    outputFile.delete();

                }


                JSONArray jsonArray = new JSONArray(str);
                JSONObject object = new JSONObject();
                object.put(jsonObjectName, jsonArray);
                FileWriter fileWriter = new FileWriter(outputFile, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(object.toString());
                bufferedWriter.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        gfgThread.start();
    }

    public Map<String, List<ManualDataDetail>> getSyncData(Context context) {
        List<ManualDataDetail> parameterList = new ArrayList<>();
        Map<String, List<ManualDataDetail>> listHashMap = new HashMap<>();


        try {
            String path = context.getFilesDir().toString() + "/Datavalue";
            String jsonObjectName = "manualDataDetailRequests";
            File directory = new File(path);
            File[] files = directory.listFiles();
            if (files != null) {
                for (File value : files) {

                    FileReader fileReader = new FileReader(value);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        stringBuilder.append(line).append("\n");
                        line = bufferedReader.readLine();
                    }
                    bufferedReader.close();
                    String response = stringBuilder.toString();
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray jArr = jsonObj.getJSONArray(jsonObjectName);
                    for (int j = 0; j < jArr.length(); j++) {
                        JSONObject jsonObject = new JSONObject(jArr.getString(j));
                        parameterList.add(new ManualDataDetail(jsonObject.getInt("manualDataDetailId"), jsonObject.getString("androidId"), jsonObject.getString("dateAndTime"), jsonObject.getInt("templateId"), jsonObject.getInt("sectionId"), jsonObject.getInt("parameterId"), jsonObject.getString("value")));
                    }
                    listHashMap.put(value.getName(), parameterList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listHashMap;
    }

    public List<ManualDataDetail> getPVDataTemplateId(Context context, String date, int templateId, String androidId, String userToken) {
        List<ManualDataDetail> parameterList = new ArrayList<>();


        try {
            String path = context.getFilesDir().toString() + "/Datavalue";

            String filename = "ParameterValue," + androidId + "," + userToken + "," + templateId + "," + date + ".json";

            File file = new File(path, filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String response = stringBuilder.toString();
            System.out.println("resssss" + response);
            JSONObject jsonObj = new JSONObject(response);
            JSONArray jArr = jsonObj.getJSONArray(jsonObjectName);
            for (int j = 0; j < jArr.length(); j++) {
                JSONObject jsonObject = new JSONObject(jArr.getString(j));
                parameterList.add(new ManualDataDetail(jsonObject.getInt("manualDataDetailId"), jsonObject.getString("androidId"), jsonObject.getString("dateAndTime"), jsonObject.getInt("templateId"), jsonObject.getInt("sectionId"), jsonObject.getInt("parameterId"), jsonObject.getString("value")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parameterList;
    }

    public void sendDataToServer(Context context, List<ManualDataDetail> manualDataDetails) {

        Thread gfgThread = new Thread(() -> {
            try {

                String str = new Gson().toJson(manualDataDetails);

                //Check Network Connection
                ConnectionDetector connectionDetector = new ConnectionDetector(context);
                if (connectionDetector.isConnectingToInternet()) {
                    JSONArray jsonArray = new JSONArray(str);
                    JSONObject object = new JSONObject();
                    object.put(jsonObjectName, jsonArray);

                    StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(gfgPolicy);

                    System.out.println("datammmmmmmmmmmmmmmmmmmmmmm" + object + userToken);

                    boolean response = jsonParser.getJSONFromUrl(ApiUrl.manualDataDetail, object, userToken);


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        gfgThread.start();
    }

    public void deleteFile(Context context) {
        try {
            String path = context.getFilesDir().toString() + "/Datavalue";
            File directory = new File(path);
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    System.out.println("filename" + file);

                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
