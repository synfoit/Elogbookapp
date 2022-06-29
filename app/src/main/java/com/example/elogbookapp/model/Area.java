package com.example.elogbookapp.model;


public class Area {
    int areaId;
    String areaName;
    Boolean isactive;
    int createBy;
    String createDate;
    int updateBy;
    String updateDate;

    public  Area(int areaId,String areaName,Boolean isactive,int createBy,String createDate,int updateBy,String updateDate){
   this.areaId=areaId;
   this.areaName=areaName;
   this.isactive=isactive;
   this.createBy=createBy;
   this.createDate=createDate;
   this.updateBy=updateBy;
   this.updateDate=updateDate;

    }
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
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
}
