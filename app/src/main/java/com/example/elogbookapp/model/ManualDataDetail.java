package com.example.elogbookapp.model;

public class ManualDataDetail {
    int manualDataDetailId;
    String androidId;
    String dateAndTime;
    int templateId;
    int sectionId;
    int parameterId;
    String value;

    public ManualDataDetail(   int manualDataDetailId,
                             String androidId,
                             String dateAndTime,
                             int templateId,
                             int sectionId,
                             int parameterId,
                             String value){
        this.manualDataDetailId=manualDataDetailId;
        this.androidId=androidId;
        this.dateAndTime=dateAndTime;
        this.templateId=templateId;
        this.sectionId=sectionId;
        this.parameterId=parameterId;
        this.value=value;
    }

    public int getManualDataDetailId() {
        return manualDataDetailId;
    }

    public void setManualDataDetailId(int manualDataDetailId) {
        this.manualDataDetailId = manualDataDetailId;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getParameterId() {
        return parameterId;
    }

    public void setParameterId(int parameterId) {
        this.parameterId = parameterId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
