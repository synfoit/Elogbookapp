package com.example.elogbookapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elogbookapp.Comman;
import com.example.elogbookapp.HomeScreen;
import com.example.elogbookapp.LoginScreen;
import com.example.elogbookapp.R;
import com.example.elogbookapp.TMScreen;
import com.example.elogbookapp.model.DrawerItem;
import com.example.elogbookapp.model.Template;
import com.google.gson.Gson;

import java.util.List;

public class DrawerAdpter extends RecyclerView.Adapter<DrawerAdpter.MyViewHolder>  {

     List<Template> drawerItemList;
     Context context;
 //   public static int lastClickedPosition = -1;
    public static int selectedItem=0;

    public DrawerAdpter(Context context, List<Template> drawerItemList) {
        this.context = context;
        this.drawerItemList = drawerItemList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customdrawerlayout, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if(position==0){
           // holder.itemView.setTag("Home");
            holder.name.setText("Home");
        }
        else {
           // holder.itemView.setTag(drawerItemList.get(position));
            holder.name.setText(drawerItemList.get(position-1).getTemplateName());
        }

       // holder.imageView.setImageResource(drawerItemList.get(position).getImage());


       /* if (selectedItem == position) {
            holder.name.setTextColor(context.getResources().getColor(R.color.purple_500));
            holder.imageView.setImageResource(drawerItemList.get(position).getBlueimage());
             }*/



        holder.itemView.setOnClickListener(v -> {

            int previousItem = selectedItem;
            selectedItem = position;

            notifyItemChanged(previousItem);
            notifyItemChanged(position);

            if (position==0) {
                Intent intent = new Intent(context, HomeScreen.class);
                context.startActivity(intent);
            } else {
                Gson gson = new Gson();
                String jsonCars = gson.toJson(drawerItemList.get(position-1).getSectionParametersList());
                Intent intent = new Intent(context, TMScreen.class);
                intent.putExtra("sectionList",jsonCars);
                intent.putExtra("templateId",drawerItemList.get(position-1).getTemplateId());
                intent.putExtra("templateName",drawerItemList.get(position-1).getTemplateName());
                context.startActivity(intent);
            }


        });

    }


    @Override
    public int getItemCount() {
        return drawerItemList.size()+1;
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.dr_text);
            imageView = view.findViewById(R.id.imageView);

        }
    }

}
