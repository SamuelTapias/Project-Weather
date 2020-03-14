package com.example.volley


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.volley.data.City
import com.example.volley.volleyUtils.CityInfo
import com.example.volley.volleyUtils.CityInfoView
import kotlinx.android.synthetic.main.fragment_main.view.*


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() ,CityAdapter.onListInteraction{


    private var cityAdapter: CityAdapter?=null
    lateinit var cityPersonalModel: City
    lateinit var navController: NavController
    lateinit var viewModel2: CityInfoView
    private var citiesList= mutableListOf<CityInfo>()
    private var count: Int=0
    val citiesL = mutableListOf<CityInfo>()


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_main, container, false)



        viewModel2= ViewModelProvider(this).get(CityInfoView::class.java)
        cityAdapter= CityAdapter(citiesL,this)

        view.list.layoutManager= LinearLayoutManager(context)
        view.list.adapter=cityAdapter
        viewModel2.getCities().observe(viewLifecycleOwner, Observer { cities ->
            run{
                citiesList = cities as MutableList<CityInfo>
                //userList.add(User("User "+count,"Apellido"+count))*/
                val temp =citiesList.size
                Log.d("project weather","userList size "+citiesList.size)
                if(count<temp){
                    citiesL.add(citiesList.get(citiesList.size-1))
                    cityAdapter!!.updateData()
                    count++
                }

            }
        })



        return view

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

    }
    /*override fun onListItemInteraction(item: RandomUser?) {
        /*if (item != null) {
            userPersonalModel= User(item.name.first,item.name.last,item.picture.medium)
            val bundle = bundleOf("data" to userPersonalModel, "name" to userPersonalModel.name,"gender" to userPersonalModel.last, "url" to userPersonalModel.url
            )
            navController!!.navigate(R.id.action_mainFragment_to_userFragment,bundle)
        }*/
    }*/



    override fun onListItemInteraction(item: CityInfo?) {
        if (item != null) {
            cityPersonalModel= City(item.name,
                (Integer.parseInt((item.dayHour.get(0).main.feels_like.get(0) + "" + item.dayHour.get(0).main.feels_like.get(1) + "" + item.dayHour.get(0).main.feels_like.get(2))) -273).toString() +" °C",
                (Integer.parseInt((item.dayHour.get(1).main.feels_like.get(0) + "" + item.dayHour.get(1).main.feels_like.get(1) + "" + item.dayHour.get(1).main.feels_like.get(2))) -273).toString() +" °C",
                (Integer.parseInt((item.dayHour.get(2).main.feels_like.get(0) + "" + item.dayHour.get(2).main.feels_like.get(1) + "" + item.dayHour.get(2).main.feels_like.get(2))) -273).toString() +" °C",
                (Integer.parseInt((item.dayHour.get(3).main.feels_like.get(0) + "" + item.dayHour.get(3).main.feels_like.get(1) + "" + item.dayHour.get(3).main.feels_like.get(2))) -273).toString() +" °C",
                item.dayHour.get(0).weather.description, item.dayHour.get(1).weather.description,item.dayHour.get(2).weather.description, item.dayHour.get(3).weather.description,
                getUrl(item.dayHour.get(0).weather.main),getUrl(item.dayHour.get(1).weather.main),getUrl(item.dayHour.get(2).weather.main),getUrl(item.dayHour.get(3).weather.main),item.dayHour.get(0).dt_txt,item.dayHour.get(1).dt_txt,item.dayHour.get(2).dt_txt,item.dayHour.get(3).dt_txt)

                val bundle = bundleOf("data" to cityPersonalModel, "name" to cityPersonalModel.name,"temps1" to cityPersonalModel.temps1, "temps2" to cityPersonalModel.temps2,
                    "temps3" to cityPersonalModel.temps3,"temps4" to cityPersonalModel.temps4,"days1" to cityPersonalModel.day1,
                    "days2" to cityPersonalModel.day2,"days3" to cityPersonalModel.day3,"days4" to cityPersonalModel.day4,
                    "url1" to cityPersonalModel.url1,"url2" to cityPersonalModel.url2,"url3" to cityPersonalModel.url3,"url4" to cityPersonalModel.url4, "date1" to cityPersonalModel.date1, "date2" to cityPersonalModel.date2
                    , "date3" to cityPersonalModel.date3, "date4" to cityPersonalModel.date4)

            navController!!.navigate(R.id.action_mainFragment_to_cityFragment,bundle)
        }
    }

    fun getUrl(state: String): String{
        var url=""
        if(state.equals("Rain")){
            url="data:image/png;base64,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"
        }else{
            if(state.equals("Clouds")){
                url="https://png.pngtree.com/png-vector/20190413/ourlarge/pngtree-vector-cloud-icon-png-image_939423.jpg"
            }else{
                url="https://imageog.flaticon.com/icons/png/512/169/169367.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF"
            }
        }
        return url

    }

}
