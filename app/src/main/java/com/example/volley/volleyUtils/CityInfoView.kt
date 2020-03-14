package com.example.volley.volleyUtils

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class CityInfoView (application: Application): AndroidViewModel(application){
    private var cityInfoDao: CityInfoDao

    init{
        cityInfoDao= CityInfoDao.getIntance(this.getApplication())
        cityInfoDao.addUser()
    }

    fun getCities(): MutableLiveData<List<CityInfo>> {
        //Log.d("project", "city: "+cityInfoDao.getCities().value!!.size)
        return cityInfoDao.getCities()
    }
}