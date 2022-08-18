package com.example.elogbookapp.model;


import java.util.ArrayList;

public class Section {
    int sectionId;
    String sectionName;
    ArrayList<Parameter> parameters;
    boolean isActive;
    int createBy;

    public boolean isActive() {
        return isActive;
    }

    public int getCreateBy() {
        return createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    String createDate;
    int updateBy;
    String updateDate;
    public Section(int sectionId,
                   String sectionName,
                   boolean isActive,
                   int createBy,
                   String createDate,
                   int updateBy,
                   String updateDate){
        this. sectionId=sectionId;
        this. sectionName=sectionName;
        this. isActive=isActive;
        this. createBy=createBy;
        this. createDate=createDate;
        this.updateBy=updateBy;
        this.updateDate=updateDate;
    }


    public Section(int sectionId,
                   String sectionName,
                   ArrayList<Parameter> parameters
                   ){
        this. sectionId=sectionId;
        this. sectionName=sectionName;
        this.parameters=parameters;

    }



    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<Parameter> parameters) {
        this.parameters = parameters;
    }
}
