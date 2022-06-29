package com.example.elogbookapp.model;

public class Dictionary {
    int dictionaryId;
    String dictionaryName;
    String dictionaryValue;
    int sortorder;
    boolean isactive;
    int createBy;
    String createDate;
    int updateBy;
    String updateDate;
    public Dictionary(int dictionaryId, String dictionaryName, String dictionaryValue, int sortorder, boolean isactive, int createBy, String createDate,int updatedBy,String updateDate)
    {
        this.dictionaryId=dictionaryId;
        this.dictionaryName=dictionaryName;
        this.dictionaryValue=dictionaryValue;
        this.sortorder=sortorder;
        this.isactive=isactive;
        this.createBy=createBy;
        this.createDate=createDate;
        this.updateBy=updatedBy;
        this.updateDate=updateDate;
    }

    public int getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(int dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getDictionaryValue() {
        return dictionaryValue;
    }

    public void setDictionaryValue(String dictionaryValue) {
        this.dictionaryValue = dictionaryValue;
    }

    public int getSortorder() {
        return sortorder;
    }

    public void setSortorder(int sortorder) {
        this.sortorder = sortorder;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
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
