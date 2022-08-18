package com.example.elogbookapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elogbookapp.Comman;
import com.example.elogbookapp.R;
import com.example.elogbookapp.model.ManualDataDetail;
import com.example.elogbookapp.model.Section;
import com.example.elogbookapp.model.Template;
import com.example.elogbookapp.repository.ParameterValueRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemplateMasterAdapater extends RecyclerView.Adapter<TemplateMasterAdapater.MyViewHolder> {
    List<Section> sectionList;
    List<Map<String, EditText>> listdata = new ArrayList<>();
    List<Map<String, String>> dropdata = new ArrayList<>();


    String uniqId;
    ParameterValueRepository parameterValueRepository ;
    Context context;
    String date;
    int  templateId;
    String userToken;
    public static int flag=0;

    public TemplateMasterAdapater(List<Section> sectionList, Context context, String uniqId, String date,  int  templateId, String userToken) {
        this.sectionList = sectionList;
        this.context = context;
        this.uniqId = uniqId;
        this.date = date;
        this.userToken=userToken;
        this.templateId = templateId;
        parameterValueRepository = new ParameterValueRepository(context, Comman.getSavedUserData(context,Comman.Key_Usertoken));
        listdata.clear();
        dropdata.clear();

    }

    @NonNull
    @Override
    public TemplateMasterAdapater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_child_listing, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"ResourceAsColor", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Section template = sectionList.get(position);
        holder.textView_parentName.setText(template.getSectionName());
        holder.indextext.setText(String.valueOf(position + 1));
        holder.subtitle.setText("Section Data: " + template.getParameters().size());


     /*   if (st.equalsIgnoreCase("temp3")) {
            if (position == 2) {
                holder.linearLayout_childItems.setVisibility(View.VISIBLE);
                holder.im_uparrow.setVisibility(View.VISIBLE);
                holder.im_downarrow.setVisibility(View.GONE);
            }
        }

        if (st.equalsIgnoreCase("temp2")) {
            if (position == 1) {
                holder.linearLayout_childItems.setVisibility(View.VISIBLE);
                holder.im_uparrow.setVisibility(View.VISIBLE);
                holder.im_downarrow.setVisibility(View.GONE);
            }
        }

        if (st.equalsIgnoreCase("temp1")) {
            if (position == 0) {
                holder.linearLayout_childItems.setVisibility(View.VISIBLE);
                holder.im_uparrow.setVisibility(View.VISIBLE);
                holder.im_downarrow.setVisibility(View.GONE);
            }
        }*/

        ParameterMasterAdapter parameterMasterAdapter = new ParameterMasterAdapter(template.getParameters(),templateId, template.getSectionId(),context, date, uniqId,userToken);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(holder.context, LinearLayoutManager.VERTICAL, false);

        holder.recyclerView.setLayoutManager(manager);
        holder.recyclerView.setAdapter(parameterMasterAdapter);
        parameterMasterAdapter.notifyDataSetChanged();

        //Get which store in local templateId vise and date using uniqId
        List<ManualDataDetail> details = parameterValueRepository.getPVDataTemplateId(context, date, templateId, uniqId,userToken);


        if (template.getParameters().size() <= details.size()) {
            holder.im_circle.setBackgroundResource(R.drawable.bg_circleblue);
            holder.indextext.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (template.getParameters().size() > details.size() && details.size() != 0) {
            holder.im_circle.setBackgroundResource(R.drawable.bg_circlered);
            holder.indextext.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.im_circle.setBackgroundResource(R.drawable.bg_circle);
            holder.indextext.setTextColor(Color.parseColor("#000000"));
        }





        //get Data from Parameter Adapter
        Map<String, EditText> adapterList = parameterMasterAdapter.getList();

        Map<String, String> dropValue = parameterMasterAdapter.getSelectionList();


            listdata.add(adapterList);
            dropdata.add(dropValue);


    }


    public List<Map<String, EditText>> getListData() {

        return listdata;
    }

    public List<Map<String, String>> getdropData() {
            //Get Value from Dropdown


        return dropdata;
    }


    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        TextView textView_parentName, subtitle, indextext;
        LinearLayout linearLayout_childItems;
        RecyclerView recyclerView;
        ImageView im_uparrow, im_downarrow, im_circle;
        RelativeLayout relativeLayout;

        @SuppressLint("ResourceAsColor")
        MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            textView_parentName = itemView.findViewById(R.id.tv_parentName);
            linearLayout_childItems = itemView.findViewById(R.id.ll_child_items);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            im_uparrow = itemView.findViewById(R.id.im_arrowup);
            im_downarrow = itemView.findViewById(R.id.im_downarrow);
            relativeLayout = itemView.findViewById(R.id.rl_parent);
            subtitle = itemView.findViewById(R.id.tv_subtitle);
            indextext = itemView.findViewById(R.id.im_index);
            im_circle = itemView.findViewById(R.id.im_circle);
            textView_parentName.setMovementMethod(new ScrollingMovementMethod());


            textView_parentName.setOnClickListener(this);
            im_downarrow.setOnClickListener(this);
            im_uparrow.setOnClickListener(this);


        }

        @SuppressLint("ResourceType")
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.im_downarrow) {
                flag=1;
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linerLayoutInvisible();

                } else {
                    linerLayoutVisible();
                }
            } else if (view.getId() == R.id.im_arrowup) {
                flag=2;
                linerLayoutInvisible();
            } else {
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linerLayoutInvisible();
                } else {
                    linerLayoutVisible();
                }
            }
        }


        public void linerLayoutVisible() {
            linearLayout_childItems.setVisibility(View.VISIBLE);
            im_uparrow.setVisibility(View.VISIBLE);
            im_downarrow.setVisibility(View.GONE);
        }

        public void linerLayoutInvisible() {
            linearLayout_childItems.setVisibility(View.GONE);
            im_uparrow.setVisibility(View.GONE);
            im_downarrow.setVisibility(View.VISIBLE);
        }
    }
}
