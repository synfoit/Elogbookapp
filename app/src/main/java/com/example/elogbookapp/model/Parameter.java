package com.example.elogbookapp.model;

public class Parameter {
    int parameterId;
    String parameterName;
    String parameterDisplayName;
    int upperLimit;
    int lowerLimit;
    int toRange;
    int unitID;
    String parameterType;
    String parameterFieldType;
    int updateBy;
    String updateDate;
    int createBy;
    String createDate;
    boolean isactive;
    int dictionaryId;
    String unitName;
    String dictionaryName;



    public Parameter(int parameterId, String parameterName, String parameterDisplayName, int upperLimit, int lowerLimit, int toRange, int unitID, String parameterType, String parameterFieldType, int createBy, String createDate, int updateBy, String updateDate, boolean isactive, int dictionaryId, String unitName, String dictionaryName) {
        this.parameterId = parameterId;
        this.parameterName = parameterName;
        this.parameterDisplayName = parameterDisplayName;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.toRange = toRange;
        this.unitID = unitID;
        this.parameterType = parameterType;
        this.parameterFieldType = parameterFieldType;
        this.createBy = createBy;
        this.createDate = createDate;
        this.updateBy=updateBy;
        this.updateDate=updateDate;
        this.isactive = isactive;
        this.dictionaryId = dictionaryId;
        this.unitName = unitName;
        this.dictionaryName = dictionaryName;

    }

    public Parameter(int parameterId, String parameterName) {
        this.parameterId=parameterId;
        this.parameterName=parameterName;
    }

    public Parameter(int parameterId, String parameterName, int upperLimit, int lowerLimit, int toRange) {
        this.parameterId=parameterId;
        this.parameterName=parameterName;
        this.upperLimit=upperLimit;
        this.lowerLimit=lowerLimit;
        this.toRange=toRange;

    }

    public Parameter(int parameterId, String parameterName, int upperLimit, int lowerLimit, int toRange, String parameterFieldType) {
        this.parameterId=parameterId;
        this.parameterName=parameterName;
        this.upperLimit=upperLimit;
        this.lowerLimit=lowerLimit;
        this.toRange=toRange;
        this.parameterFieldType=parameterFieldType;
    }

    public Parameter(int parameterId, String parameterName, String parameterDisplayName, int upperLimit, int lowerLimit, int toRange, String parameterType, String parameterFieldType, int dictionaryId) {
        this.parameterId=parameterId;
        this.parameterDisplayName=parameterDisplayName;
        this.parameterName=parameterName;
        this.upperLimit=upperLimit;
        this.lowerLimit=lowerLimit;
        this.toRange=toRange;
        this.parameterFieldType=parameterFieldType;
        this.parameterType=parameterType;
        this.dictionaryId=dictionaryId;
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

    public String getParameterDisplayName() {
        return parameterDisplayName;
    }

    public void setParameterDisplayName(String parameterDisplayName) {
        this.parameterDisplayName = parameterDisplayName;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public int getToRange() {
        return toRange;
    }

    public void setToRange(int toRange) {
        this.toRange = toRange;
    }

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterFieldType() {
        return parameterFieldType;
    }

    public void setParameterFieldType(String parameterFieldType) {
        this.parameterFieldType = parameterFieldType;
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

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public int getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(int dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }
}
