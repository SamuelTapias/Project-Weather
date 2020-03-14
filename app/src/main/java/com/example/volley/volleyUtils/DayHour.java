package com.example.volley.volleyUtils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kotlinx.android.parcel.Parcelize;

public class DayHour {
    public Main main;
    public Weather weather;
    public String dt_txt;


    public DayHour(String fl, String tmpmin, String tmpmax){
        main= new Main(fl, tmpmin,tmpmax);
        weather=null;
        dt_txt=null;
    }
    public static ArrayList<DayHour> getDayHour(JSONObject response){
        ArrayList<DayHour> list = new ArrayList<>();
        try{
            JSONArray info = response.getJSONArray("list");
                try {
                    //first day
                    DayHour temp = new DayHour(info.getJSONObject(0).getJSONObject("main").getString("feels_like"),
                            info.getJSONObject(0).getJSONObject("main").getString("temp_min"),
                            info.getJSONObject(0).getJSONObject("main").getString("temp_max"));
                    temp.dt_txt = info.getJSONObject(0).getString("dt_txt");
                    temp.weather = Weather.getWeather(info.getJSONObject(0));
                    list.add(temp);
                    //second day
                    temp = new DayHour(info.getJSONObject(8).getJSONObject("main").getString("feels_like"),
                            info.getJSONObject(8).getJSONObject("main").getString("temp_min"),
                            info.getJSONObject(8).getJSONObject("main").getString("temp_max"));
                    temp.dt_txt = info.getJSONObject(8).getString("dt_txt");
                    temp.weather = Weather.getWeather(info.getJSONObject(8));
                    list.add(temp);
                    //3 day
                    temp = new DayHour(info.getJSONObject(16).getJSONObject("main").getString("feels_like"),
                            info.getJSONObject(16).getJSONObject("main").getString("temp_min"),
                            info.getJSONObject(16).getJSONObject("main").getString("temp_max"));
                    temp.dt_txt = info.getJSONObject(16).getString("dt_txt");
                    temp.weather = Weather.getWeather(info.getJSONObject(16));
                    list.add(temp);
                    //4 day
                    temp = new DayHour(info.getJSONObject(24).getJSONObject("main").getString("feels_like"),
                            info.getJSONObject(24).getJSONObject("main").getString("temp_min"),
                            info.getJSONObject(24).getJSONObject("main").getString("temp_max"));
                    temp.dt_txt = info.getJSONObject(24).getString("dt_txt");
                    temp.weather = Weather.getWeather(info.getJSONObject(24));
                    list.add(temp);

                }catch (Exception e1) {
                    e1.printStackTrace();
                }
        }catch (JSONException e ){
            e.printStackTrace();
        }
        return list;
    }
    @Parcelize
    public static class Main{
        public String feels_like;
        public String temp_min;
        public String temp_max;
        public Main(String fl, String tmpmin, String tmpmax){
            feels_like=fl;
            temp_max=tmpmax;
            temp_min=tmpmin;
        }


    }

    @Override
    public String toString(){
        return "- Min_Temp: "+main.temp_min+" Max_Temp: "+main.temp_max+" Feels_Like: "+main.feels_like+" " +weather+ " date: "+ dt_txt+" -";
    }
}
