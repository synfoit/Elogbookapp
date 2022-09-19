package com.example.elogbookapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.elogbookapp.repository.JSONParser;
import com.google.android.material.textfield.TextInputEditText;
import com.vicmikhailau.maskededittext.MaskedEditText;
import com.vicmikhailau.maskededittext.MaskedFormatter;
import com.vicmikhailau.maskededittext.MaskedWatcher;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Date;

public class AddDeviceId extends AppCompatActivity {

    TextInputEditText device_id;
    AppCompatButton id_button;
    MaskedEditText maskedEditText;
    JSONParser jsonParser;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device_id);
        jsonParser = new JSONParser();
        maskedEditText = findViewById(R.id.et_masked);
        MaskedFormatter formatter = new MaskedFormatter("###-###-###-###");
        maskedEditText.addTextChangedListener(new MaskedWatcher(formatter, maskedEditText));
        id_button = findViewById(R.id.bt_add);
        id_button.setOnClickListener(view -> {
            Thread gfgThread = new Thread(() -> {
                String licenseId = maskedEditText.getText().toString();
                System.out.println("deviceId" + licenseId);
                if (!licenseId.isEmpty()) {
                    StringBuilder result = new StringBuilder();
                    try {
                        result.append(URLEncoder.encode("uuid", "UTF-8"));
                        result.append("=");
                        result.append(URLEncoder.encode(Comman.androidUniqeId(AddDeviceId.this), "UTF-8"));
                        JSONObject jsonObject = jsonParser.registerDevice(ApiUrl.registerDevice + "91BF-22DA-75F3", result.toString());

                        String decorated = TrippleDes.Decrypt(jsonObject.getString("licenseKey"), Comman.Key_license);
                        System.out.println(decorated);
                        JSONObject encryptData = new JSONObject(decorated);
                        Date StarDate = Comman.dateFormat.parse(encryptData.getString("StartDate"));
                        Date EndDate = Comman.dateFormat.parse(encryptData.getString("EndDate"));
                        Date CurrentDate = new Date();
                        if (encryptData.getString("SubscriptionType").equalsIgnoreCase("Trial")) {
                            if (!CurrentDate.before(StarDate) || !CurrentDate.after(EndDate)) {
                                Comman.saveUUID(AddDeviceId.this, Comman.Key_UNIQUE_ID, jsonObject.getString("androidUUID"));
                                Intent i = new Intent(AddDeviceId.this, LoginScreen.class);
                                startActivity(i);
                                finish();
                            } else {
                                Intent i = getIntent();
                                startActivity(i);
                                finish();
                            }

                        } else if (encryptData.getString("SubscriptionType").equalsIgnoreCase("Paid")) {
                            Comman.saveUUID(AddDeviceId.this, Comman.Key_UNIQUE_ID, jsonObject.getString("androidUUID"));
                            Intent i = new Intent(AddDeviceId.this, LoginScreen.class);
                            startActivity(i);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });

            gfgThread.start();

        });


    }
}