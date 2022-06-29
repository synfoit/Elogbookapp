package com.example.elogbookapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SectionParameters implements Parcelable {


    protected SectionParameters(Parcel in) {
        sectionId = in.readInt();
        sectionName = in.readString();
        parameterId = in.readInt();
        parameterName = in.readString();
        templateId = in.readInt();

    }

    public static final Creator<SectionParameters> CREATOR = new Creator<SectionParameters>() {
        @Override
        public SectionParameters createFromParcel(Parcel in) {
            return new SectionParameters(in);
        }

        @Override
        public SectionParameters[] newArray(int size) {
            return new SectionParameters[size];
        }
    };

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

    public int getParameterId() {
        return parameterId;
    }

    public void setParameterId(int parameterId) {
        this.parameterId = parameterId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    int sectionId;
    String sectionName;
    int parameterId;
    String parameterName;
    int templateId;

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    String parameterValue;


    public  SectionParameters(int sectionId,
            String sectionName,
            int parameterId,
            String parameterName,
            int templateId){

        this.sectionId=sectionId;
        this. sectionName=sectionName;
        this. parameterId=parameterId;
        this. parameterName=parameterName;
        this. templateId=templateId;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(sectionId);
        parcel.writeString(sectionName);
        parcel.writeInt(parameterId);
        parcel.writeString(parameterName);
        parcel.writeInt(templateId);

    }
}
