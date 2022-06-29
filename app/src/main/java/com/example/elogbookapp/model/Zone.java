package com.example.elogbookapp.model;
public class Zone {
    int zoneId;
    String zoneName;
    boolean isactive;
    int createBy;
    String createDate;
    int updateBy;
    String updateDate;
    public Zone(int zoneId,
                String zoneName,
                boolean isactive,
                int createBy,
                String createDate,
                int updateBy,
                String updateDate){

        this. zoneId=zoneId;
        this. zoneName=zoneName;
        this. isactive=isactive;
        this. createBy=createBy;
        this. createDate=createDate;
        this. updateBy=updateBy;
        this. updateDate=updateDate;

    }
}
