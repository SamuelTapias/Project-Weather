package com.example.volley.volleyUtils

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.volley.VolleySingleton
import org.json.JSONObject

class CityInfoDao private constructor(var context: Context) {
    private val cities= MutableLiveData<List<CityInfo>>()
    private val citiesList= mutableListOf<CityInfo>()
    private val urls= mutableListOf<String>()
    private var queue: RequestQueue

    init{
        queue = VolleySingleton.getIntance(context).requestQueue
        urls.add("http://api.openweathermap.org/data/2.5/forecast?q=Bogota&appid=678b3c6d079f043247e7c5e3c4650fd1")
        urls.add("http://api.openweathermap.org/data/2.5/forecast?q=Medellin&appid=678b3c6d079f043247e7c5e3c4650fd1")
        urls.add("http://api.openweathermap.org/data/2.5/forecast?q=Barranquilla&appid=678b3c6d079f043247e7c5e3c4650fd1")
        urls.add("http://api.openweathermap.org/data/2.5/forecast?q=Cartagena&appid=678b3c6d079f043247e7c5e3c4650fd1")
        urls.add("http://api.openweathermap.org/data/2.5/forecast?q=Monteria&appid=678b3c6d079f043247e7c5e3c4650fd1")
        urls.add("http://api.openweathermap.org/data/2.5/forecast?q=Bucaramanga&appid=678b3c6d079f043247e7c5e3c4650fd1")
        urls.add("http://api.openweathermap.org/data/2.5/forecast?q=Manizales&appid=678b3c6d079f043247e7c5e3c4650fd1")
        urls.add("http://api.openweathermap.org/data/2.5/forecast?q=Ibague&appid=678b3c6d079f043247e7c5e3c4650fd1")
        urls.add("http://api.openweathermap.org/data/2.5/forecast?q=Sincelejo&appid=678b3c6d079f043247e7c5e3c4650fd1")
        urls.add("http://api.openweathermap.org/data/2.5/forecast?q=Cali&appid=678b3c6d079f043247e7c5e3c4650fd1")
    }
    companion object{
        @Volatile
        private var INSTANCE: CityInfoDao? = null
        fun getIntance(context: Context) =
            INSTANCE ?: synchronized (this){
                INSTANCE ?: CityInfoDao(context).also{
                    INSTANCE =it
                }
            }
    }

    fun addUser(){

        val size: Int =urls.size
        val i : Int =0
        for (i in 0.. size-1){
            VolleySingleton.getIntance(context).addToRequestQueue(getJsonObjectRequest(i))
        }

    }

    fun getCities()= cities
    fun getJsonObjectRequest(i: Int): JsonObjectRequest {
        val url =urls.get(i)
        val jsonObjectRequestRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { response ->
                parseObjectG(response)
            },
            Response.ErrorListener { error ->
                Log.d("Project Weather", "That didnt work!"+error.message)
            }
        )
        return jsonObjectRequestRequest
    }

    fun parseObjectG(response: JSONObject){
        var city = CityInfo.getCity(response)
        Log.d("Project Weather", " city name: "+city.name+ " data: " +city.dayHour)
        if(city.dayHour.get(0).weather.main.equals("Rain")){
            city.image="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAABI1BMVEX///8hl9j3sjkXi7/gmy0rpfcHktal0OwAkNYAjtUAnvYhmNkAhLwAld8VlNcio/cAg7v2+/0aoff8szEZjcQek9Dzrje01+/u9/7/tCX9sy778eP3rSPtqDTnojDf7vjR5vXD3/KcyurQ6P3tw4r//PjfmCLkpkn6zozmnCB6uuRLpt2u2Pue0PtttOI4n9vA4PyLyPpouvny06z4uVD72qrekgDquXb605r89Or4vV16kZbkr0x9ttfN4u+0qH/Mq2kzlcWZw91MsPh0v/lytuP03cHvypjnr1r24sn5w2/84r3/7dm8mVqqlm6IlIZtkZpUkKSgtLiIlImjoINgp89EkK+AoKhpnrbdrlaZpJaLoqCbpJPAqXVjnbmIu9ng3MxWANZ4AAAKyUlEQVR4nO1da1vbNhRuKDG5EAcnTZMAa5IOyCgtlMtgHVBI6Mq20q3dmpZ2XPb/f8Xs2DJJLFlHsmRJLO+3Po8fc96e26tzbOfBA5VYvXh7vnLQ7c4O0f31YGX37dKqUpMEYmn3oLu/v762tjYbwv3H+v5ad+XtK9XWJcZvK7P7o9TGsba23z1fUm1jAlwcrO0T2Xl48t3jmery3o+GenK3G0vvyeMZhOry77+ptpYZq+fk2Bw6b2YMriMN4xjPb4Kej+W9d6qthmM3jt9IdE5y/NmQorPU3efhN+T4g2rjITiPqy+x/Lx8nNE+HZe6Mfyw+WeYG3djHPgEwG/oRp2z8SAmAyEODNyobVFdjYtQWgaOUfxJNRU8Xs0mjtCQopbJuBTTBOERiij+rppOFEvrYiI0QPVn1YQmIZigfhRfCaoxoxS1CtTVmCLDSVCzchPTJrgJuhR/VM0rxIEUgi7FC9XMAuySlQxzmxhHVY/xxhKZIGOjjzLUo6DGJGFCgpqk4opEgi5F9QeNC2lJ6EN9nJJjNGkS+lB+lHpHdqEQgm61UbzeIPITE6MeQ7XS5lxqmfGxrLIprkoSM2NQ6kSyC8WUGR8qnZiGC10nqhvbvCMee0W6UGU5JfdCkS5U2BPJckasC2dm9hQxJCtSsS5Up05nvUX8+h3uxomCCaqqNRf767Pv//jz8PDDx48fDg8P//zjfdclvCZOztxBSZi2/vrQqz0aR+3h57/fz65XhTNMPUyd+uDhwvxDDDyenz8dLQomWU33JNwZzGPZhZif/3wysyiUYorD00b/YSw9N1Q9WNaXo6ZARy6nxc85jnGfyy2TKRYzPuzS5demOIbpDBadY3zyeajVQm6ZO45HomI1nZFUnRiftcwku4Cj9U1QzUnjCNXoLZCCE09viFLvVEyoyp9I9Qn+i+U3DNV/xESqZH7OAO9AGr+hGwdHAiJ1We4JqkXIQAA/z432aXI3ylU1HYIDIfR8N14lTsZlmQ9L9fEEYQ4MKP6TlKLMUzCeINyBPsUvCSlKVKa3WIIsDhRCUR5DvAeZCSYOVGmH4LoogknLjSxR8xpPkIOfR/Ea1zSqAdQwbGDbIB8/F9bXMR7VxWbz8dfT6xMX16df95rNGBErKUp7Aj3owt66Y7DYnDn9dlOzSyGs2s23670mQRzIYXiMcyE/QdeJl34qVptHn27skmVP/A9YpdLgCn9yllJLsUnIVWRClE5cFy1WTwalko2/wnVq76QadaSUjo8L0UeJCLoEXH5XNRI9RLJ2FeEoQ7XhYjQpwYx9eWKX6JeV7KuJ6iphjNHCxWhCfh5FAL8hx9r1WPuUcHoaCK4yzChdPh51o3CCuDKTOEbZYBdHJiDiB6a4VpgqPw+lb4ii+HaIc2GyRsFH8XJRVrPAZWHq/DyKN34uCn9aAVdIUy0zIayenEJzi+mF6cfoEPagKuNkgSGoxoUurJum+DTE1RlVBIcDEJH93mk0HJxgU+ZCj+InMTP9Rv120PPWm5gyqtKFHsV/k9N7fdxbiNvspixnJmBvJaTn9AlbeT2C1IX1NBG/W8paXnmQusg1+An2ye7zVvKqqQWwN3n5tXokfo9Ii101yLV5HWgEPRf2BhfBBHvP1MHjRIcQoTry48pEhxCgqqmQkHNYGWIH2po60IM1x0hwgAtRbR3ogVHYYCf2WhNk7PrYvaBa8UkFU5hi12Z6e5CxmuIGTcq1JxU2nCB2q6TafjoYEhHXKHTR2DGwOlCCuGecNK8yQ8BPiVpM7DlgPwMSbGsz7mUE+HyBa/YGxGgGrmrMLDMegAxxrcIMF2aKLVC/wOwkDHGh2xHz1uYclSWmkqo2nAW2ZW1S+mI0SE3ohaOwcxvtGIKY1aAxQRrCzsU0/040DVXbywNrgzjUwLw4YViQ+rCLpIoTZWhaGgawiwQvRhmal4Y+SCLu/jDMWHglfo8YZvLYxhhlqNrOBMAK1bn7xDBXxzCM9kPVZiYBzolRTaPayiTItTAU7xVD7OhmkqDBtTSDD9PIEMNohnmMsKnfK4a4RHQmGRqqS31YuH4RWVoYzRC3kIp0RNVWJgF+Dn6fEhG/VJyUpiYnIjYPo7VGtZkJQHjMZtKJBodpnjDLuD/9oognGNkgqraTG+SF24R0M9aJOfL4+57UGlKQPog8b2KoE2P33hNLNjPLqRVDMFJtVBvLA+yYhkjRwDil7/XHKer73CUR9CdOx59jN42ihZtCRXC8YCxFqw0h6JbUUTcaRNHOgDw4RH/klRljKObIC1IMvNeewoqq2nQQrCKlTUThfZJ0PqCouxvtXJH1mXbfkZ3bwfyCy3O+ZmmMXH7rWZuHX4DW685cv9+f0xb1NvNLF1NMMcUUU0wxxRRTGAqG9xjhlzpwPZng6wkg1DP5/Abs+LxdKFS2QZa3NvJ54JHueblQ2IEf39kxl/dOYHnInzirZLPZyhngylbO+05iHnKqe1Fwb1ouyKPo5PxDJuAFo5eeLdls4Tn90k3/Q5CkVd8IGv5NyzsAW/nQ8RniHxcbx5vy0JgswImWf1PAy3UvK/5N5TmxExhj/0K9dCdgWPiedmUQGC6oN0UMK9swe9nRyAe20D9g8DwwBhBRG8HnSvFPFYyi5UepS1HaWT40hloWHGRMgZpedRQZ9PRGkVEBpDcfUCICXoRDiVimR1TAMEOv0ShMIenNiS0UptS3ir9HTsxSb/oMOZH+miu6Jz29efEUHlFnKKJe0q5sociwqOn1Ap7enAgLH/3l97DW0CMKnt6NsNZIU2+/IGPo3QvlDL17hekd82RBAJTelRcwe9nRhnevbWQMvdage+bbtCsZ0psXWzK6V5je9K93nKGbUtObF1K6VwM1DKHpzQv0wXS6OH0Jj6hNcHo7ZXB68+JWRvdiSW/kRGniNOxedHHK0L2QlKB/oCRM7wLQYHYwdK+wYYgUp2dailNq93IY0lt+rUGLbvqX/IwXp4DuBRen6OzJIE7fAA1mhoOMoY9W5IpTenrzQo44RV2fnt478sUpcqJQcSolvXnBMVopyxCnBcPEaXgQhqe3vMlpqJQViVN4evOCoXuFStlQcSp0tMIhTstAg9nBIU7po5U5w8UpvXsZL07pEfUMRYZp4hSFKVyc5qg3TUGcShmt8IhTaVsaKaMVhvROQZxKmZxqJU7D7jUVp0ziNKw1OolToXs/eHo/CGuNDuIU3r3a8C1NCuIURZQtQ5zSl7AM6c0LKXu//4E4ZTh7yhenqCOKnZzCxWkoJah/nxdyxCnH2VOaOA33fkJHK/A5F8PZkxfwySmDOK1rNTmVMlrRU5yK3PsxpHcoJXQQp/Du1YAvYeWLUzmjFZ7JqbRao1qcvklPnArtXjziFGgwO6Ts/UwVp/DudbeE1UGcylnrGy5O6d0rFKeAsyd64EOeOJWy92MQp/KfOZWy9/sfiNNQSrRpV6YoToV2rzC96a+vwKUEL6R0r4ZWz7drI07lTU5l7P30FKdC9356ilN496Lv/eZkPCHICyROAduG8L29Nu1KB94Sw/f2pLVEJE4BDFH3AhxZg/SGvLMq/YCBuhfkFWE/oiBHgSC9Ib8SG6S3vJO+WxYs7x1oyM9ItgsuxUoFUtifehQt0O8aem9WlwvygtSzppjZgP0SaOtNNrsN61ydDXsL+OOb29nsTkyd+Q8t1IevzFFtLwAAAABJRU5ErkJggg=="
        }else{
            if(city.dayHour.get(0).weather.main.equals("Clouds")){
                city.image="https://png.pngtree.com/png-vector/20190413/ourlarge/pngtree-vector-cloud-icon-png-image_939423.jpg"
            }else{
                city.image="https://imageog.flaticon.com/icons/png/512/169/169367.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF"
            }
        }
        citiesList.add(city)
        cities.value=citiesList
        Log.d("Project Weather", " cities size: "+ (cities.value as MutableList<CityInfo>).size)
    }
}