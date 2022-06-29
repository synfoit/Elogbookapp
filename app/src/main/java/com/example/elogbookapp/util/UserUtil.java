package com.example.elogbookapp.util;

import android.content.Context;
import android.util.Log;

import com.example.elogbookapp.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class UserUtil {
    public  List<User> getUserData(Context context) {
        List<User> userList=new ArrayList<>();
        File file = new File(context.getFilesDir(),"User");
        FileReader fileReader ;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String responce = stringBuilder.toString();


            JSONArray jArr = new JSONArray(responce);
            for (int i=0; i < jArr.length(); i++) {
                JSONObject jsonObject  = new JSONObject(jArr.getString(i));


                userList.add(new User(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getString("email"),jsonObject.getString("role"),jsonObject.getString("password"),jsonObject.getString("userToken"),jsonObject.getBoolean("isDeleted"),jsonObject.getString("phoneNumber")));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
