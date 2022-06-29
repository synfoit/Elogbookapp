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

import java.util.List;

public class DrawerAdpter extends RecyclerView.Adapter<DrawerAdpter.MyViewHolder>  {

     List<DrawerItem> drawerItemList;
     Context context;
 //   public static int lastClickedPosition = -1;
    public static int selectedItem=0;

    public DrawerAdpter(Context context, List<DrawerItem> drawerItemList) {
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
        holder.itemView.setTag(drawerItemList.get(position));
        holder.name.setText(drawerItemList.get(position).getName());
        holder.imageView.setImageResource(drawerItemList.get(position).getImage());


        if (selectedItem == position) {
            holder.name.setTextColor(context.getResources().getColor(R.color.purple_700));
            holder.imageView.setImageResource(drawerItemList.get(position).getBlueimage());
             }



        holder.itemView.setOnClickListener(v -> {

            int previousItem = selectedItem;
            selectedItem = position;

            notifyItemChanged(previousItem);
            notifyItemChanged(position);

            if (drawerItemList.get(position).getName().equalsIgnoreCase("Home")) {
                Intent intent = new Intent(context, HomeScreen.class);
                context.startActivity(intent);
            } else if (drawerItemList.get(position).getName().equalsIgnoreCase("Chillier")) {
                Intent intent = new Intent(context, TMScreen.class);
                intent.putExtra("temp","temp0");
                context.startActivity(intent);
            }
            else if (drawerItemList.get(position).getName().equalsIgnoreCase("logout")) {
                Comman.deleteUserData(context);
                Intent intent = new Intent(context, LoginScreen.class);
                context.startActivity(intent);
            }

        });

    }


    @Override
    public int getItemCount() {
        return drawerItemList.size();
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
