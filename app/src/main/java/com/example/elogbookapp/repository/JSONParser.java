
package com.example.elogbookapp.repository;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JSONParser {
    static JSONObject jObj = null;
    static String json = "";

    public boolean getJSONFromUrl(String url1, JSONObject params, String token) {
        // Making HTTP request
        String responeString = "";
        try {
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("token", token);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);
            conn.setUseCaches(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = params.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                responeString = response.toString();

            }

            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;


    }


    public String uploadToUrl(String url1, JSONObject params) {
        // Making HTTP request
        String responeString = "";
        try {
            // defaultHttpClient
            URL url = new URL(url1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);
            conn.setUseCaches(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = params.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                responeString = response.toString();
            }

            if (conn.getResponseCode() == 200) {
                return responeString;
            }

            return responeString;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responeString;

    }


    public String getJsonParser(String Url) {

        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        try {
            URL url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            json = "";
            jObj = null;

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            json = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;

    }

    public JSONObject getSyncDate(String Url, String token) {


        try {

            URL url = new URL(Url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("token", token);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            jObj = new JSONObject(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jObj;


    }

    public String getLincesData(String Url,String data) {

        System.out.println(""+Url+data);
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        try {
            URL url = new URL(Url+data);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            json = "";
            jObj = null;

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            json = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;

    }



}
