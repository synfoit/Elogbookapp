package com.example.elogbookapp.util;

import android.content.Context;

import com.example.elogbookapp.model.Parameter;
import com.example.elogbookapp.model.Section;
import com.example.elogbookapp.model.Template;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
            String responce = stringBuilder.toString();

            JSONArray jaTemplatelist = new JSONArray(responce);

            int updateby = 0;
            String updateDate = "";
            //templateList.add(new Template(0, "Home", "", "", "", "", "", 0, 0, 0, 0, 0, 0, "", 0, "", false, null));
            List<Parameter> parameterList = parameterUtil.getParameterData(context);
            for (int i = 0; i < jaTemplatelist.length(); i++) {
                JSONObject jsontemplate = new JSONObject(jaTemplatelist.getString(i));

                JSONObject templateSectionDetails = jsontemplate.getJSONObject("templateSectionDetails");
                templateSectionDetails.getInt("templateId");
                JSONArray sectionParameterDetails = templateSectionDetails.getJSONArray("sectionParameterDetails");

                ArrayList<Section> sectionlist = new ArrayList<>();

                for (int k = 0; k < sectionParameterDetails.length(); k++) {
                    JSONObject data = sectionParameterDetails.getJSONObject(k);
                    JSONArray getparameterList = data.getJSONArray("parameters");
                    ArrayList<Parameter> parameters = new ArrayList<>();
                    for (int p = 0; p < getparameterList.length(); p++) {
                        JSONObject jsonparameter = getparameterList.getJSONObject(p);
                        for (int l = 0; l < parameterList.size(); l++) {
                            Parameter parameter = parameterList.get(l);
                            if (jsonparameter.getInt("parameterId") == parameter.getParameterId()) {
                                parameters.add(new Parameter(jsonparameter.getInt("parameterId"), jsonparameter.getString("parameterName"), parameter.getUpperLimit(), parameter.getLowerLimit(), parameter.getToRange(),parameter.getParameterFieldType()));

                            }
                        }
                    }
                    sectionlist.add(new Section(data.getInt("sectionId"), data.getString("sectionName"), parameters));
                }

                templateList.add(new Template(jsontemplate.getInt("templateId"), jsontemplate.getString("templateName"), jsontemplate.getString("parameterName"), jsontemplate.getString("parameterDetail"), jsontemplate.getString("plantName"), jsontemplate.getString("zoneName"), jsontemplate.getString("locationName"), jsontemplate.getInt("parameterId"), jsontemplate.getInt("plantId"), jsontemplate.getInt("zoneId"), jsontemplate.getInt("locationId"), jsontemplate.getInt("dictionaryId"), jsontemplate.getInt("createBy"), jsontemplate.getString("createDate"), updateby, updateDate, jsontemplate.getBoolean("isActive"), sectionlist));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return templateList;
    }
}
