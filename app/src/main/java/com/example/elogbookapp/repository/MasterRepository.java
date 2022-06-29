package com.example.elogbookapp.repository;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.example.elogbookapp.ApiUrl;
import com.example.elogbookapp.Comman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class MasterRepository {
    JSONParser jsonParser = new JSONParser();
    Comman comman;
    public void setMasterData(Context context,String url,String fileName) {
        comman=new Comman(context);
        Thread gfgThread = new Thread(() -> {
            try {
                comman.intertnetStricNode();

                String jsonData = jsonParser.getJsonParser(url);
                File file = new File(context.getFilesDir(), fileName);
                FileWriter fileWriter = new FileWriter(file,true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(jsonData);
                bufferedWriter.close();
                Log.d("jsonData", jsonData);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        gfgThread.start();

    }
}
