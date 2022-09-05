package com.example.elogbookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

public class AddDeviceId extends AppCompatActivity {

    TextInputEditText device_id;
    AppCompatButton id_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device_id);
        device_id = findViewById(R.id.tv_deviceId);
        id_button = findViewById(R.id.bt_add);
        id_button.setOnClickListener(view -> {
            String deviceId = device_id.getText().toString();
            if (!deviceId.isEmpty()) {
                Comman.saveUUID(AddDeviceId.this, Comman.Key_UNIQUE_ID, deviceId);
                Intent i = new Intent(AddDeviceId.this, LoginScreen.class);
                startActivity(i);
                finish();
            }
        });


    }
}