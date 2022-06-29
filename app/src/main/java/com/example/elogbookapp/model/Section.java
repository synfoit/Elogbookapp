package com.example.elogbookapp.model;



public class Section {
    int sectionId;
    String sectionName;
    boolean isActive;
    int createBy;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
}
