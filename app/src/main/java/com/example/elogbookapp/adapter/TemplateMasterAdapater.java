package com.example.elogbookapp.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elogbookapp.ApiUrl;
import com.example.elogbookapp.Comman;
import com.example.elogbookapp.HomeScreen;
import com.example.elogbookapp.LicenseScreen;
import com.example.elogbookapp.LoginScreen;
import com.example.elogbookapp.R;
import com.example.elogbookapp.model.ManualDataDetail;
import com.example.elogbookapp.model.Section;

import com.example.elogbookapp.repository.ParameterValueRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemplateMasterAdapater extends RecyclerView.Adapter<TemplateMasterAdapater.MyViewHolder> {
    List<Section> sectionList;
    List<Map<String, EditText>> listdata = new ArrayList<>();
    List<Map<String, String>> dropdata = new ArrayList<>();


    String uniqueId;
    ParameterValueRepository parameterValueRepository;
    Context context;
    String date;
    int templateId;
    String userToken;
    public static int flag = 0;
    ProgressDialog pd;

    public TemplateMasterAdapater(List<Section> sectionList, Context context, String uniqueId, String date, int templateId, String userToken) {
        this.sectionList = sectionList;
        this.context = context;
        this.uniqueId = uniqueId;
        this.date = date;
        this.userToken = userToken;
        this.templateId = templateId;
        parameterValueRepository = new ParameterValueRepository(context, Comman.getSavedUserData(context, Comman.Key_Usertoken));
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

        ParameterMasterAdapter parameterMasterAdapter = new ParameterMasterAdapter(template.getParameters(), templateId, template.getSectionId(), context, date, uniqueId, userToken);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(holder.context, LinearLayoutManager.VERTICAL, false);
        holder.recyclerView.setLayoutManager(manager);
        holder.recyclerView.setAdapter(parameterMasterAdapter);

        holder.im_down_arrow.setOnClickListener(view -> {
            //  expand(view);
            holder.progressBar.setVisibility(View.VISIBLE);


            holder.linearLayout_childItems.setVisibility(View.VISIBLE);
            holder.im_up_arrow.setVisibility(View.VISIBLE);
            holder.im_down_arrow.setVisibility(View.GONE);

           // parameterMasterAdapter.notifyDataSetChanged();
            holder.progressBar.setVisibility(View.GONE);


        });
        holder.im_up_arrow.setOnClickListener(view -> {
            //collapse(view);
            ProgressDialog dialog = new ProgressDialog(context);

            dialog.setMessage("Please wait.....");
            dialog.show();


            holder.linearLayout_childItems.setVisibility(View.GONE);
            holder.im_up_arrow.setVisibility(View.GONE);
            holder.im_down_arrow.setVisibility(View.VISIBLE);
            dialog.dismiss();
        });
       /* holder.itemView.setOnClickListener(view -> {

            ProgressDialog dialog = new ProgressDialog(context);

            dialog.setMessage("Please wait.....");
            dialog.show();
            if (holder.linearLayout_childItems.getVisibility() == View.VISIBLE) {
                holder.linearLayout_childItems.setVisibility(View.GONE);
                holder.im_up_arrow.setVisibility(View.GONE);
                holder.im_down_arrow.setVisibility(View.VISIBLE);
                dialog.dismiss();
            } else {
                holder.linearLayout_childItems.setVisibility(View.VISIBLE);
                holder.im_up_arrow.setVisibility(View.VISIBLE);
                holder.im_down_arrow.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });*/

        //Get which store in local templateId vise and date using uniqueId
        List<ManualDataDetail> details = parameterValueRepository.getPVDataTemplateId(context, date, templateId, uniqueId, userToken);


        if (template.getParameters().size() <= details.size()) {
            holder.im_circle.setBackgroundResource(R.drawable.bg_circleblue);
            holder.indextext.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (template.getParameters().size() > details.size() && details.size() != 0) {
            holder.im_circle.setBackgroundResource(R.drawable.bg_circlered);
            holder.indextext.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
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

    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
        ProgressDialog dialog;
        Context context;
        TextView textView_parentName, subtitle, indextext;
        LinearLayout linearLayout_childItems;
        RecyclerView recyclerView;
        ImageView im_up_arrow, im_down_arrow, im_circle;
        RelativeLayout relativeLayout;
        ProgressBar progressBar;

        @SuppressLint("ResourceAsColor")
        MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            textView_parentName = itemView.findViewById(R.id.tv_parentName);
            linearLayout_childItems = itemView.findViewById(R.id.ll_child_items);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            im_up_arrow = itemView.findViewById(R.id.im_arrowup);
            im_down_arrow = itemView.findViewById(R.id.im_downarrow);
            relativeLayout = itemView.findViewById(R.id.rl_parent);
            subtitle = itemView.findViewById(R.id.tv_subtitle);
            indextext = itemView.findViewById(R.id.im_index);
            im_circle = itemView.findViewById(R.id.im_circle);
            progressBar=itemView.findViewById(R.id.pb_view);
            textView_parentName.setMovementMethod(new ScrollingMovementMethod());
            /*itemView.setOnClickListener(view -> {
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linerLayoutInvisible();

                } else {

                    linerLayoutVisible();

                }
            });*/
         /*   textView_parentName.setOnClickListener(this);
            im_down_arrow.setOnClickListener(this);
            im_up_arrow.setOnClickListener(this);*/


        }

      /*  @SuppressLint("ResourceType")
        @Override
        public void onClick(View view) {
            dialog = new ProgressDialog(recyclerView.getContext());
            dialog.setMessage("Please wait.....");
            dialog.show();
            if (view.getId() == R.id.im_downarrow) {
                flag = 1;
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linerLayoutInvisible();

                } else {

                    linerLayoutVisible();

                }
            } else if (view.getId() == R.id.im_arrowup) {
                flag = 2;
                linerLayoutInvisible();
            } else {
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linerLayoutInvisible();

                } else {

                    linerLayoutVisible();

                }
            }
        }*/


        public void linerLayoutVisible() {
            linearLayout_childItems.setVisibility(View.VISIBLE);
            im_up_arrow.setVisibility(View.VISIBLE);
            im_down_arrow.setVisibility(View.GONE);
            dialog.dismiss();
        }

        public void linerLayoutInvisible() {
            linearLayout_childItems.setVisibility(View.GONE);
            im_up_arrow.setVisibility(View.GONE);
            im_down_arrow.setVisibility(View.VISIBLE);
            dialog.dismiss();
        }
    }


}
