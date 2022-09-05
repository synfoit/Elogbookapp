package com.example.elogbookapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.text.InputFilter;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elogbookapp.R;
import com.example.elogbookapp.model.Dictionary;
import com.example.elogbookapp.model.ManualDataDetail;
import com.example.elogbookapp.model.Parameter;

import com.example.elogbookapp.repository.ParameterValueRepository;
import com.example.elogbookapp.util.DictinaryUtil;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterMasterAdapter extends RecyclerView.Adapter<ParameterMasterAdapter.MyViewHolder> {
    ArrayList<Parameter> parameterMasterList;

    MyViewHolder holder;
    View rootView;
    Context context;
    int templateId, sectionId;

    Map<String, EditText> editTextHashMap = new HashMap<>();
    Map<String, String> dropdownHashMap = new HashMap<>();

    ParameterValueRepository parameterValueRepository;

    String date, uniqueID, token;
    String[] dataType = {"Dropdown", "Numeric", "Text", "Checkbox"};

    public ParameterMasterAdapter(ArrayList<Parameter> parameterMasterList, int templateId, int sectionId, Context context, String date, String uniqueID, String token) {
        this.parameterMasterList = parameterMasterList;
        this.templateId = templateId;
        this.context = context;
        this.date = date;
        this.token = token;
        this.sectionId = sectionId;
        this.uniqueID = uniqueID;
        parameterValueRepository = new ParameterValueRepository(context, token);
        editTextHashMap.clear();
        dropdownHashMap.clear();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, upper_limit, lowerlimit, range;
        public EditText editText;
        public TextInputLayout selection;
        AutoCompleteTextView droptext;
        ScrollView scrollView;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_parameterName);
            editText = view.findViewById(R.id.et_paravalue);
            selection = view.findViewById(R.id.til_dropdown);
            droptext = view.findViewById(R.id.act_dropdown);
            upper_limit = view.findViewById(R.id.tv_uppervalue);
            lowerlimit = view.findViewById(R.id.tv_lower);
            range = view.findViewById(R.id.tv_range);
            scrollView = itemView.findViewById(R.id.scrollView);


        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custome_template, parent, false);
        context = parent.getContext();
        rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        this.holder = holder;

        Parameter parametersection = parameterMasterList.get(position);

        holder.title.setText(parametersection.getParameterName());


        List<ManualDataDetail> details = parameterValueRepository.getPVDataTemplateId(context, date, templateId, uniqueID, token);

        //here we take parameter data from local store then compare with SectionParameters then show in list


        holder.upper_limit.setText(String.valueOf(parametersection.getUpperLimit()));
        holder.lowerlimit.setText(String.valueOf(parametersection.getLowerLimit()));
        holder.range.setText(String.valueOf(parametersection.getToRange()));
        holder.title.setSelected(true);
        holder.title.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //Enabling scrolling on TextView.
        holder.title.setMovementMethod(new ScrollingMovementMethod());

        ManualDataDetail dataDetail = null;
        if (details.size() != 0) {
            for (int a = 0; a < details.size(); a++) {
                dataDetail = details.get(a);
                if (parametersection.getParameterFieldType().equalsIgnoreCase(dataType[0])) {
                    setDropdownValue(parametersection, dataDetail);
                } else if (parametersection.getParameterFieldType().equalsIgnoreCase(dataType[2])) {
                    holder.editText.setFilters(new InputFilter[]{});
                    setTextValue(parametersection, dataDetail);

                } else if (parametersection.getParameterFieldType().equalsIgnoreCase(dataType[1])) {
                    holder.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    setTextValue(parametersection, dataDetail);
                } else if (parametersection.getParameterFieldType().equalsIgnoreCase(dataType[3])) {
                    holder.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    setTextValue(parametersection, dataDetail);
                }
            }

        } else {
            if (parametersection.getParameterFieldType().equalsIgnoreCase(dataType[0])) {
                setDropdownValue(parametersection, dataDetail);
            } else if (parametersection.getParameterFieldType().equalsIgnoreCase(dataType[2])) {
                setTextValue(parametersection, dataDetail);
            } else if (parametersection.getParameterFieldType().equalsIgnoreCase(dataType[1])) {
                holder.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                setTextValue(parametersection, dataDetail);
            } else if (parametersection.getParameterFieldType().equalsIgnoreCase(dataType[3])) {
                holder.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                setTextValue(parametersection, dataDetail);
            }
        }


    }


    public void setDropdownValue(Parameter parameter, ManualDataDetail dataDetail) {


        holder.droptext.setVisibility(View.VISIBLE);
        holder.selection.setVisibility(View.VISIBLE);

        DictinaryUtil dictinaryUtil = new DictinaryUtil();
        List<Dictionary> dictionaryList = dictinaryUtil.getDictionaryData(context);

        List<String> dictionaryValue = new ArrayList<>();
        for (int p = 0; p < dictionaryList.size(); p++) {
            Dictionary dictionary = dictionaryList.get(p);
            if (parameter.getDictionaryId() == dictionary.getDictionaryId()) {
                dictionaryValue.add(dictionary.getDictionaryName());
            }
        }

        ArrayAdapter<String> adapterType = new ArrayAdapter<>(
                context,
                R.layout.layout,
                dictionaryValue
        );

        holder.droptext.setAdapter(adapterType);

        if (dataDetail != null) {
            if (parameter.getParameterId() == dataDetail.getParameterId() && sectionId == dataDetail.getSectionId()) {

                holder.droptext.setSelection(dictionaryValue.indexOf(dataDetail.getValue()) + 1);
                holder.droptext.setText(dataDetail.getValue());

                dropdownHashMap.put(templateId + "," + parameter.getParameterId() + "," + sectionId, dataDetail.getValue());

                holder.droptext.setOnItemClickListener((adapterView, view, i, l) -> {
                    String parameterValue = (String) adapterView.getItemAtPosition(i);
                    if (parameterValue == null) {
                        dropdownHashMap.put(templateId + "," + parameter.getParameterId() + "," + sectionId, dataDetail.getValue());

                    } else {
                        dropdownHashMap.put(templateId + "," + parameter.getParameterId() + "," + sectionId, parameterValue);
                    }

                });
            } else {
                holder.droptext.setOnItemClickListener((adapterView, view, i, l) -> {
                    String parameterValue = (String) adapterView.getItemAtPosition(i);
                    dropdownHashMap.put(templateId + "," + parameter.getParameterId() + "," + sectionId, parameterValue);
                });
            }
        } else {
            holder.droptext.setOnItemClickListener((adapterView, view, i, l) -> {
                String parameterValue = (String) adapterView.getItemAtPosition(i);
                dropdownHashMap.put(templateId + "," + parameter.getParameterId() + "," + sectionId, parameterValue);
            });
        }

    }

    public void setTextValue(Parameter section, ManualDataDetail dataDetail) {
        holder.editText.setVisibility(View.VISIBLE);
        if (dataDetail != null) {
            if (section.getParameterId() == dataDetail.getParameterId() && sectionId == dataDetail.getSectionId()) {
                holder.editText.setText(dataDetail.getValue());
            }
        }
        editTextHashMap.put(templateId + "," + section.getParameterId() + "," + sectionId, holder.editText);

    }


    public Map<String, EditText> getList() {
        return editTextHashMap;
    }

    public Map<String, String> getSelectionList() {
        return dropdownHashMap;
    }

    @Override
    public int getItemCount() {
        return parameterMasterList.size();
    }


}
