package com.example.elogbookapp.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;


import com.example.elogbookapp.Comman;
import com.example.elogbookapp.model.ManualDataDetail;
import com.example.elogbookapp.model.Template;
import com.example.elogbookapp.repository.ParameterValueRepository;

import java.util.List;
import java.util.Map;

public class SendDataToServer {
    TemplateUtil templateUtil;
    List<Template> templateList;
    ParameterValueRepository pvr;

    public String syncDataToServer(Context context, String userToken, String uniqueID) {
        pvr = new ParameterValueRepository(context, userToken);
        templateUtil = new TemplateUtil();
        templateList = templateUtil.getTemplateData(context);
        Map<String, List<ManualDataDetail>> listMap = pvr.getSyncData(context);
        for (Map.Entry<String, List<ManualDataDetail>> entry : listMap.entrySet()) {
            String[] value = entry.getKey().split(",");
            String compareDate = value[4].substring(0, 10);

            int storeTemplateId = Integer.parseInt(value[3]);

            for (int k = 0; k < templateList.size(); k++) {
                Template template = templateList.get(k);
                int templateId = template.getTemplateId();

                if (storeTemplateId == templateId) {
                    List<ManualDataDetail> manualDataDetails = pvr.getPVDataTemplateId(context, compareDate, storeTemplateId, uniqueID, userToken);

                    int sectionParamCount = 0;
                    for (int m = 0; m < template.getSectionParametersList().size(); m++) {
                        sectionParamCount += template.getSectionParametersList().get(m).getParameters().size();

                    }
                    if (sectionParamCount == manualDataDetails.size()) {
                        if (pvr.sendDataToServer(context, manualDataDetails)) {
                            new Handler(Looper.getMainLooper()).post(() -> Comman.getToast("TemplateID:" + templateId + "  Date:" + compareDate + "  Sync", context));

                        }

                    } else {
                        new Handler(Looper.getMainLooper()).post(() -> Comman.getToast("TemplateID:" + templateId + "  not complete  " + compareDate + "on Date:", context));

                    }
                }
            }
        }


        return "";
    }
}
