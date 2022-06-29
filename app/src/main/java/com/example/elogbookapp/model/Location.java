package com.example.elogbookapp.model;


public class Location {
    int locationId;
    String locationName;
    boolean isactive;
    int createBy;
    String createDate;
    int updateBy;
    String updateDate;

    public  Location( int locationId,String locationName,boolean isactive,int createBy,String createDate,int updateBy,String updateDate){
        this.locationId=locationId;
        this.locationName=locationName;
        this.isactive=isactive;
        this.createBy=createBy;
        this.createDate=createDate;
        this.updateBy=updateBy;
        this.updateDate=updateDate;
    }
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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
