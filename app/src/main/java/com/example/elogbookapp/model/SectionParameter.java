package com.example.elogbookapp.model;

public class SectionParameter {
    int parameterId;
    String parameterName;


    public SectionParameter(int parameterId,
                            String parameterName){
        this.parameterId=parameterId;
        this.parameterName=parameterName;

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


}
