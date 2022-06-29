package com.example.elogbookapp.model;

import java.util.ArrayList;

public class Template {
    int templateId;
    String templateName;
    String parameterName;
    String parameterDetail;
    String plantName;
    String zoneName;
    String locationName;
    int parameterId;
    int plantId;
    int zoneId;
    int locationId;
    int dictionaryId;
    int createBy;
    String createDate;
    int updateBy;
    String updateDate;
    boolean isActive;

    ArrayList<SectionParameters> sectionParametersList;
    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getParameterId() {
        return parameterId;
    }

    public void setParameterId(int parameterId) {
        this.parameterId = parameterId;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(int dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public ArrayList<SectionParameters> getSectionParametersList() {
        return  sectionParametersList;
    }

    public void setSectionParametersList(ArrayList<SectionParameters> sectionParametersList) {
        this.sectionParametersList = sectionParametersList;
    }




    public Template(int templateId, String templateName,String parameterName,String parameterDetail, String plantName, String zoneName, String locationName, int parameterId, int plantId, int zoneId, int locationId, int dictionaryId, int createBy, String createDate, int updateBy, String updateDate, boolean isactive, ArrayList<SectionParameters> sectionParametersList) {
        this. templateId=templateId;
        this. templateName=templateName;
        this. parameterName=parameterName;
        this. parameterDetail=parameterDetail;
        this. plantName=plantName;
        this. zoneName=zoneName;
        this. locationName=locationName;
        this. parameterId=parameterId;
        this. plantId=plantId;
        this. zoneId=zoneId;
        this. locationId=locationId;
        this. dictionaryId=dictionaryId;
        this. createBy=createBy;
        this. createDate=createDate;
        this. updateBy=updateBy;
        this. updateDate=updateDate;
        this.isActive=isactive;
        this.sectionParametersList=sectionParametersList;
    }
}
