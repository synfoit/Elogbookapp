package com.example.elogbookapp.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elogbookapp.Comman;
import com.example.elogbookapp.R;
import com.example.elogbookapp.model.ManualDataDetail;
import com.example.elogbookapp.model.Section;
import com.example.elogbookapp.repository.ParameterValueRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TemplateMasterAdapater extends RecyclerView.Adapter<TemplateMasterAdapater.MyViewHolder> {
    List<Section> sectionList;
    List<Map<String, EditText>> listdata = new ArrayList<>();
    List<Map<String, String>> dropdata = new ArrayList<>();

    ProgressDialog progress;
    String uniqueId;
    ParameterValueRepository parameterValueRepository;
    Context context;
    String date;
    int templateId;
    String userToken;

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

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            progressBar = itemView.findViewById(R.id.pb_view);
            textView_parentName.setMovementMethod(new ScrollingMovementMethod());
            itemView.setOnClickListener(view -> {
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linerLayoutInvisible();

                } else {

                    linerLayoutVisible();

                }
            });
            textView_parentName.setOnClickListener(this);
            im_down_arrow.setOnClickListener(this);
            im_up_arrow.setOnClickListener(this);


        }

        @SuppressLint("ResourceType")
        @Override
        public void onClick(View view) {
            progress = new ProgressDialog(recyclerView.getContext());
            progress.setMessage("Please wait.....");
            progress.show();
            new Thread() {
                public void run() {
                    // do the long operation on this thread
                    // after retrieving the data then use it to build the views and close the dialog on the main UI thread

                    try {
                        ((AppCompatActivity) context).runOnUiThread(() -> {
                            // remove the retrieving of data from this method and let it just build the views
                            if (view.getId() == R.id.im_downarrow) {

                                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                                    linerLayoutInvisible();
                                    progress.dismiss();
                                } else {
                                    linerLayoutVisible();
                                    progress.dismiss();
                                }
                            } else if (view.getId() == R.id.im_arrowup) {

                                linerLayoutInvisible();
                                progress.dismiss();
                            } else {
                                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                                    linerLayoutInvisible();
                                    progress.dismiss();

                                } else {

                                    linerLayoutVisible();
                                    progress.dismiss();
                                }


                            }

                        });
                    } catch (Exception e) {
                        Log.e("tag", e.getMessage());
                    }
                }
            }.start();


        }


        public void linerLayoutVisible() {
            linearLayout_childItems.setVisibility(View.VISIBLE);
            im_up_arrow.setVisibility(View.VISIBLE);
            im_down_arrow.setVisibility(View.GONE);

        }

        public void linerLayoutInvisible() {
            linearLayout_childItems.setVisibility(View.GONE);
            im_up_arrow.setVisibility(View.GONE);
            im_down_arrow.setVisibility(View.VISIBLE);

        }
    }




}
