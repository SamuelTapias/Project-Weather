package com.example.volley.volleyUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CityInfo {
    public String name;
    public String country;
    public ArrayList<DayHour> dayHour;
    public String image;
    public String mainTemp;

    public CityInfo(JSONObject ci){
        try{
            this.name=ci.getJSONObject("city").getString("name")+",";
            this.country=ci.getJSONObject("city").getString("country");
        }catch (JSONException e ){
            e.printStackTrace();
        }
    }

    public static CityInfo getCity(JSONObject response){
        CityInfo temp=null;
        try{

            temp =new CityInfo(response);
            temp.dayHour=DayHour.getDayHour(response);
            temp.mainTemp=""+(Integer.parseInt(""+temp.dayHour.get(0).main.feels_like.charAt(0)+""+temp.dayHour.get(0).main.feels_like.charAt(1)+""+temp.dayHour.get(0).main.feels_like.charAt(2))-273)+" °C";
        }catch (Exception e ){
            e.printStackTrace();
        }
        return temp;
    }
}
