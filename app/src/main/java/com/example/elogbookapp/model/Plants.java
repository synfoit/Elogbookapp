package com.example.elogbookapp.model;

public class Plants {
    int plantId;
    String plantName;
    boolean isactive;
    int createBy;
    String createDate;
    int updateBy;
    String updateDate;
    public Plants(int plantId,
                  String plantName,
                  boolean isactive,
                  int createBy,
                  String createDate,
                  int updateBy,
                          String updateDate
    ){
        this. plantId=plantId;
        this. plantName=plantName;
        this. isactive=isactive;
        this. createBy=createBy;
        this. createDate=createDate;
        this.updateBy=updateBy;
        this.updateDate=updateDate;

    }
}
