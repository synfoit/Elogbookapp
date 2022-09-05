package com.example.elogbookapp.util;

import android.content.Context;
import android.os.Build;

import com.example.elogbookapp.model.Parameter;
import com.example.elogbookapp.model.Section;
import com.example.elogbookapp.model.Template;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TemplateUtil {
    public List<Template> getTemplateData(Context context) {
        List<Template> templateList = new ArrayList<>();
        File file = new File(context.getFilesDir(), "Template");
        ParameterUtil parameterUtil = new ParameterUtil();
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String response = stringBuilder.toString();

            JSONArray mainTemplateList = new JSONArray(response);


            List<Parameter> parameterList = parameterUtil.getParameterData(context);
            for (int i = 0; i < mainTemplateList.length(); i++) {
                JSONObject jsonTemplate = new JSONObject(mainTemplateList.getString(i));

                JSONObject templateSectionDetails = jsonTemplate.getJSONObject("templateSectionDetails");

                JSONArray sectionParameterDetails = templateSectionDetails.getJSONArray("sectionParameterDetails");

                ArrayList<Section> sectionList = new ArrayList<>();

                for (int k = 0; k < sectionParameterDetails.length(); k++) {
                    JSONObject data = sectionParameterDetails.getJSONObject(k);
                    JSONArray getParameterList = data.getJSONArray("parameters");
                    ArrayList<Parameter> parametersList = new ArrayList<>();
                    for (int p = 0; p < getParameterList.length(); p++) {
                        JSONObject jsonParameter = getParameterList.getJSONObject(p);
                        for (int l = 0; l < parameterList.size(); l++) {
                            Parameter parameter = parameterList.get(l);
                            if (jsonParameter.getInt("parameterId") == parameter.getParameterId()) {
                                parametersList.add(parameter);

                            }
                        }
                    }
                    sectionList.add(new Section(data.getInt("sectionId"), data.getString("sectionName"), parametersList));
                }
                templateList.add(new Template(jsonTemplate.getInt("templateId"), jsonTemplate.getString("templateName"), sectionList));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return templateList;
    }

    public List<Template> getTemplateData1(Context context) {
        List<Template> templateList = new ArrayList<>();
        File file = new File(context.getFilesDir(), "Template");
        ParameterUtil parameterUtil = new ParameterUtil();
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String response = stringBuilder.toString();


            JSONObject jsonObject = new JSONObject(response);

            JSONArray mainTemplateList = new JSONArray(response);

            //jsonObject.getJSONArray("sectionParameterDetails").stea


            List<Parameter> parameterList = parameterUtil.getParameterData(context);
            for (int i = 0; i < mainTemplateList.length(); i++) {
                JSONObject jsonTemplate = new JSONObject(mainTemplateList.getString(i));


                JSONObject templateSectionDetails = jsonTemplate.getJSONObject("templateSectionDetails");

                JSONArray sectionParameterDetails = templateSectionDetails.getJSONArray("sectionParameterDetails");

                ArrayList<Section> sectionList = new ArrayList<>();

                for (int k = 0; k < sectionParameterDetails.length(); k++) {
                    JSONObject data = sectionParameterDetails.getJSONObject(k);
                    JSONArray getParameterList = data.getJSONArray("parameters");
                    ArrayList<Parameter> parametersList = new ArrayList<>();
                    for (int p = 0; p < getParameterList.length(); p++) {
                        JSONObject jsonParameter = getParameterList.getJSONObject(p);
                        for (int l = 0; l < parameterList.size(); l++) {
                            Parameter parameter = parameterList.get(l);
                            if (jsonParameter.getInt("parameterId") == parameter.getParameterId()) {
                                parametersList.add(new Parameter(jsonParameter.getInt("parameterId"), jsonParameter.getString("parameterName"), parameter.getUpperLimit(), parameter.getLowerLimit(), parameter.getToRange(), parameter.getParameterFieldType()));

                            }
                        }
                    }
                    sectionList.add(new Section(data.getInt("sectionId"), data.getString("sectionName"), parametersList));
                }
                templateList.add(new Template(jsonTemplate.getInt("templateId"), jsonTemplate.getString("templateName"), jsonTemplate.getString("parameterName"), jsonTemplate.getString("parameterDetail"), jsonTemplate.getInt("parameterId"), jsonTemplate.getInt("dictionaryId"), sectionList));

                // templateList.add(new Template(jsonTemplate.getInt("templateId"), jsonTemplate.getString("templateName"), jsonTemplate.getString("parameterName"), jsonTemplate.getString("parameterDetail"), jsonTemplate.getString("plantName"), jsonTemplate.getString("zoneName"), jsonTemplate.getString("locationName"), jsonTemplate.getInt("parameterId"), jsonTemplate.getInt("plantId"), jsonTemplate.getInt("zoneId"), jsonTemplate.getInt("locationId"), jsonTemplate.getInt("dictionaryId"), jsonTemplate.getInt("createBy"), jsonTemplate.getString("createDate"), updateBy, updateDate, jsonTemplate.getBoolean("isActive"), sectionList));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return templateList;
    }
}
