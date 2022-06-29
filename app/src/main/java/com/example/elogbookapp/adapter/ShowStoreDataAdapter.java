package com.example.elogbookapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elogbookapp.R;
import com.example.elogbookapp.model.ManualDataDetail;
import com.example.elogbookapp.model.Parameter;

import com.example.elogbookapp.model.SectionParameters;
import com.example.elogbookapp.model.Template;
import com.example.elogbookapp.util.ParameterUtil;
import com.example.elogbookapp.util.TemplateUtil;

import java.util.List;

public class ShowStoreDataAdapter  extends RecyclerView.Adapter<ShowStoreDataAdapter.MyViewHolder>  {

     List<ManualDataDetail> manualDataDetails;
     Context context;
     TemplateUtil templateUtil=new TemplateUtil();

     ParameterUtil parameter=new ParameterUtil();

    public ShowStoreDataAdapter(Context context, List<ManualDataDetail> manualDataDetails) {
        this.context = context;
        this.manualDataDetails = manualDataDetails;

    }

    @NonNull
    @Override
    public ShowStoreDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_list_item, parent, false);

        return new MyViewHolder(itemView);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ShowStoreDataAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            int index=position+1;
            holder.index.setText(""+index);
            ManualDataDetail manualDataDetail=manualDataDetails.get(position);
            List<Template> templateList=templateUtil.getTemplateData(context);
            List<Parameter> parameterList=parameter.getParameterData(context);

            for (int m=0;m<templateList.size();m++){
                Template template=templateList.get(m);
                if (template.getTemplateId()==manualDataDetail.getTemplateId())
                {
                    holder.template.setText(template.getTemplateName());
                }
                for (int n=0;n<template.getSectionParametersList().size();n++)
                {
                    SectionParameters parameters=template.getSectionParametersList().get(n);
                    if (parameters.getSectionId()==manualDataDetail.getSectionId()){
                    holder.section.setText(parameters.getSectionName());}
                }
            }

            for (int p=0;p<parameterList.size();p++){
                Parameter parameter=parameterList.get(p);
                if (parameter.getParameterId()==manualDataDetail.getParameterId())
                {
                    holder.parameter.setText(parameter.getParameterName());
                }
            }





            holder.value.setText(manualDataDetail.getValue());


    }


    @Override
    public int getItemCount() {
        return manualDataDetails.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView template,section,parameter,index,value;



        public MyViewHolder(View view) {
            super(view);
            template =  view.findViewById(R.id.tv_template);
            section=view.findViewById(R.id.tv_section);
            parameter=view.findViewById(R.id.tv_parameter);
            value=view.findViewById(R.id.tv_value);
            index=view.findViewById(R.id.im_index);


        }
    }

}
