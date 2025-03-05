package com.example.e_commerce.security.amqp.producer.producerConfig;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProducerConfig {

      public String getLocation() {
            try {
                  String url = "http://ipinfo.io/json";
                  HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                  connection.setRequestMethod("GET");

                  BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                  String inputLine;
                  StringBuffer response = new StringBuffer();
                  while ((inputLine = reader.readLine()) != null) {
                        response.append(inputLine);
                  }
                  reader.close();

                  JSONObject info = new JSONObject(response.toString());

                  String city = info.optString("city", "Unknown");
                  String state = info.optString("region", "Unknown");
                  String country = info.optString("country", "Unknown");

                  return country + ", "+ state+", "+city+".";

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return "";
      }
}
