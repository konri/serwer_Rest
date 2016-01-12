package com.engineer.utilites;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Created by Konrad on 06.01.2016.
 */
public class SendGCMNotification implements Runnable{
    private static final String keyServer = "AIzaSyDSP7cHH9COEG1I0Jf0i782nntRuPES_tU";
    private String registrationId;
    private JSONObject data;

    public SendGCMNotification(String registrationId, JSONObject data) {
        this.registrationId = registrationId;
        this.data = data;
    }

    @Override
    public void run() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        try {
            Logger.getLogger(this.getClass()).info("Thread GCM start");
            HttpPost request = new HttpPost("https://android.googleapis.com/gcm/send");
            JSONObject jsonObject = new JSONObject();

            Logger.getLogger(this.getClass()).info("registration: " + registrationId);
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(registrationId);
            jsonObject.put("registration_ids", jsonArray);
            jsonObject.put("data", data);

            StringEntity params =new StringEntity(jsonObject.toString());
            Logger.getLogger(this.getClass()).info("JSON object: " + jsonObject.toString());
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", "key=" + keyServer);
            request.setEntity(params);

            CloseableHttpResponse response = httpClient.execute(request);
            Logger.getLogger(this.getClass()).info("Response from GCM: " + response.toString());

        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
