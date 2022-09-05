package com.example.elogbookapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elogbookapp.adapter.DrawerAdpter;
import com.example.elogbookapp.model.DrawerItem;
import com.example.elogbookapp.model.Template;
import com.example.elogbookapp.model.User;
import com.example.elogbookapp.repository.JSONParser;
import com.example.elogbookapp.util.TemplateUtil;
import com.example.elogbookapp.util.UserUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.telephony.TelephonyManager;
import android.content.Context;

import org.json.JSONObject;

public class Comman {

    List<DrawerItem> list;
    DrawerItem drawerBean;
    DrawerLayout dLayout;
    Context context;
    RecyclerView dList;
    DrawerAdpter drawerAdpter;
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat dateAndTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    static SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
    public static final String SHARED_PREF = "userData";
    public static final String SHARED_PREF_DATE = "DataDeleteDate";
    public static final String SHARED_PREF_UUID = "AndroidUUID";
    public static String Key_UNIQUE_ID = "Android_DeviceUniqueID";
    public static String Key_Usertoken = "UserToken";
    public static String Key_Date = "StoreDate";
    public static String Key_Update = "LastUpDate";
    public static String Key_license = "s5u8x/A?D(G+KbPe";
    UserUtil userUtil = new UserUtil();
    TemplateUtil templateUtil;
    static List<Template> templateList;

    public Comman(Context context) {
        this.context = context;
    }

    void sideBar(Toolbar toolbar, final Context context, final Activity activity) {

        ActionBarDrawerToggle mDrawerToggle;
        TextView userImage, imageView, userTest;


        int[] icon = {R.drawable.ic_baseline_home_24, R.drawable.ic_baseline_insert_drive_file_24, R.drawable.ic_baseline_logout_black_24};
        int[] blue_icon = {R.drawable.ic_baseline_home_blue_24, R.drawable.ic_baseline_insert_drive_blue_file_24, R.drawable.ic_baseline_logout_24};

        String[] Sidebar_Title = {"Home", "Chillier", "Logout"};
        templateUtil = new TemplateUtil();
        templateList = templateUtil.getTemplateData(activity);

        list = new ArrayList<>();
        for (int i1 = 0; i1 < Sidebar_Title.length; i1++) {
            drawerBean = new DrawerItem(Sidebar_Title[i1], icon[i1], blue_icon[i1]);
            list.add(drawerBean);

        }


        userImage = activity.findViewById(R.id.tvemail);
        userTest = activity.findViewById(R.id.tvUsername);
        imageView = activity.findViewById(R.id.imageView);

        String userToken = Comman.getSavedUserData(context, Comman.Key_Usertoken);

        List<User> userList = userUtil.getUserData(context);
        for (int k = 0; k < userList.size(); k++) {
            User user = userList.get(k);
            if (userToken.equalsIgnoreCase(user.getUserToken())) {
                userTest.setText(user.getName());
                userImage.setText(user.getEmail());
                imageView.setText(user.getName().substring(0, 2));
            }
        }


        ///set list
        dLayout = activity.findViewById(R.id.drawer_layout);
        dList = activity.findViewById(R.id.left_drawer);
        drawerAdpter = new DrawerAdpter(context, templateList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        dList.setLayoutManager(linearLayoutManager);

        dList.setAdapter(drawerAdpter);

        mDrawerToggle = new ActionBarDrawerToggle(activity, dLayout,
                toolbar,
                R.string.Open, R.string.Close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        mDrawerToggle.getDrawerArrowDrawable().setColor(context.getResources().getColor(R.color.purple_500));
        dLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }

    public static void getToast(String message, Context context) {

        new Handler(Looper.getMainLooper()).post(() -> {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
        });

    }

    public static void saveUserData(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);

        editor.apply();
    }

    public static String getSavedUserData(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, 0);
        return pref.getString(key, "");

    }

    public static void saveUpdateDate(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);

        editor.apply();
    }

    public static String getUpdateDate(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF, 0);
        return pref.getString(key, "");

    }

    public static void deleteUpdateData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Comman.SHARED_PREF_DATE, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void deleteUserData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Comman.SHARED_PREF, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void saveDateofDeleteData(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_DATE, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getDateofDeleteData(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_DATE, 0);
        return pref.getString(key, "");

    }

    public static void deleteDateData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Comman.SHARED_PREF_DATE, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void saveUUID(Context context, String key, String value) {
        Log.d("key//",key);
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_UUID, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getUUID(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_UUID, 0);
        return pref.getString(key, "");

    }

    public void intertnetStricNode() {
        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);
    }
    public static boolean getLicenseModule(Context context) {

        try {
            JSONParser   jp=new JSONParser();
            String lancesValue = jp.getLincesData(ApiUrl.license, Comman.getUUID(context, Comman.Key_UNIQUE_ID));
            JSONObject object = new JSONObject(lancesValue);
            String encryptedValue = object.getString("licenseKey");
            System.out.println(encryptedValue);
            String decorated = TrippleDes.Decrypt(encryptedValue, Comman.Key_license);
            System.out.println(decorated);
            JSONObject encryptData = new JSONObject(decorated);
            Date StarDate = Comman.dateFormat.parse(encryptData.getString("StartDate"));
            Date EndDate = Comman.dateFormat.parse(encryptData.getString("EndDate"));
            Date CurrentDate = new Date();

            return !CurrentDate.before(StarDate) || !CurrentDate.after(EndDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static long dateDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();


        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        return elapsedDays;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("HardwareIds")
    public static String androidUniqeId(Context context) throws SecurityException, NullPointerException {


        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
