package com.example.volley.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(val name: String,
                val temps1: String,
                val temps2: String,
                val temps3: String,
                val temps4: String,
                val day1 :String,
                val day2 :String,
                val day3 :String,
                val day4 :String,
                val url1 :String,
                val url2 :String,
                val url3 :String,
                val url4 :String,
                val date1 : String,
                val date2 : String,
                val date3 : String,
                val date4 : String) : Parcelable {
}