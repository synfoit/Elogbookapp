package com.example.elogbookapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elogbookapp.database.UserData;
import com.example.elogbookapp.repository.JSONParser;
import com.example.elogbookapp.repository.MasterRepository;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;


public class LoginScreen extends AppCompatActivity {
    Button bt_login;
    TextInputEditText et_username, et_password;
    ProgressDialog pd;
    String username, password;
    Comman comman;
    ConnectionDetector connectionDetector;

    JSONParser jsonParser;

    UserData userData;
    MasterRepository  masterRepository = new MasterRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        bt_login = findViewById(R.id.bt_login);
        et_username = findViewById(R.id.et_userid);
        et_password = findViewById(R.id.et_password);

        pd = new ProgressDialog(this);
        comman = new Comman(LoginScreen.this);
        userData = new UserData(LoginScreen.this);
        connectionDetector = new ConnectionDetector(LoginScreen.this);
        jsonParser = new JSONParser();


        masterRepository.setMasterData(LoginScreen.this, ApiUrl.user, "User");




        bt_login.setOnClickListener(view -> {


            if (connectionDetector.isConnectingToInternet()) {
                try {
                    username = Objects.requireNonNull(et_username.getText()).toString();
                    password = Objects.requireNonNull(et_password.getText()).toString();

                    if (username.isEmpty()) {
                        Comman.getToast("Please Enter UserName", LoginScreen.this);
                    } else if (password.isEmpty()) {
                        Comman.getToast("Please Enter Password", LoginScreen.this);
                    } else {
                        JSONObject object = new JSONObject();
                        object.put("message", "Login");
                        object.put("Username", username);
                        object.put("password", password);
                        object.put("token", "3fa85f64-5717-4562-b3fc-2c963f66afa6");
                        object.put("HasMessage", false);

                        new SendfeedbackJob().execute(object);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Comman.getToast("Please connect internet", LoginScreen.this);
            }

        });
    }

    @SuppressLint("StaticFieldLeak")
    private class SendfeedbackJob extends AsyncTask<JSONObject, Void, String> {

        @Override
        protected void onPreExecute() {
            pd.setMessage("Login...");
            pd.show();
        }

        @Override
        protected String doInBackground(JSONObject[] params) {
            // do above Server call here
            comman.intertnetStricNode();
            return jsonParser.uploadToUrl(ApiUrl.userLogin, params[0]);
        }

        @Override
        protected void onPostExecute(String response) {
            if (pd != null)
                pd.dismiss();
            if (!response.equals("")) {
                String message = "";
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    String token = jsonObject.getString("token");
                    message = jsonObject.getString("message");

                    Comman.saveUserData(LoginScreen.this, Comman.Key_Usertoken, token);
                    userData.adduserToken(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getAllMasterData();


                if (Comman.getSavedUserData(LoginScreen.this, Comman.Key_Usertoken).length() != 0 && message.equalsIgnoreCase("Success")) {
                    Thread gfgThread = new Thread(() -> {

                        if (Comman.getLicenseModule(LoginScreen.this)) {
                            Intent i = new Intent(LoginScreen.this, HomeScreen.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(LoginScreen.this, LicenseScreen.class);
                            startActivity(i);
                            finish();
                        }
                    });

                    gfgThread.start();
                }
            } else {
                Comman.getToast("Please Correct Username and Password ", LoginScreen.this);

            }

        }
    }

    public void getAllMasterData() {

        masterRepository.setMasterData(LoginScreen.this, ApiUrl.dictionary, "Dictionary");
        masterRepository.setMasterData(LoginScreen.this, ApiUrl.parameter, "Parameter");
        masterRepository.setMasterData(LoginScreen.this, ApiUrl.section, "Section");
        masterRepository.setMasterData(LoginScreen.this, ApiUrl.template, "Template");

    }



    @Override
    public void onBackPressed() {
        Intent i = getIntent();
        startActivity(i);

    }


}