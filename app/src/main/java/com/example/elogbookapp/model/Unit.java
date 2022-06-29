package com.example.elogbookapp.model;

public class Unit {
    int unitId;
    String unitName;
    boolean isactive;
    int createBy;
    String createDate;
    int updateBy;
    String updateDate;
    public Unit(int unitId,
                String unitNam,
                boolean isactive,
                int createBy,
                String createDate,
                int updateBy,
                String updateDate){
        this. unitId=unitId;
        this. unitName=unitNam;
        this. isactive=isactive;
        this. createBy=createBy;
        this. createDate=createDate;
        this. updateBy=updateBy;
        this. updateDate=updateDate;
    }
}
