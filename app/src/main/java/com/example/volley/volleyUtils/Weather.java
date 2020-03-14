package com.example.volley.volleyUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Weather {
    public String id;
    public String main;
    public String description;

    public Weather(JSONObject we){
        try{
            this.id=we.getString("id");
            this.main=we.getString("main");
            this.description=we.getString("description");
        }catch (JSONException e ){
            e.printStackTrace();
        }
    }

    public static Weather getWeather(JSONObject response){
        Weather temp=null;
        try{
            JSONArray info = response.getJSONArray("weather");
            temp =new Weather(info.getJSONObject(0));
        }catch (JSONException e ){
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public String toString(){
        return "Main weather: "+main+" Description weather "+ description;
    }
}
