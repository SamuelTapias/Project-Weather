package com.example.volley


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.volley.data.City
import com.example.volley.databinding.FragmentCityBinding
import com.example.volley.volleyUtils.CityInfo

/**
 * A simple [Fragment] subclass.
 */
class CityFragment : Fragment() {

    lateinit var mBinding : FragmentCityBinding


    lateinit var city: City
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_user, container, false)
        mBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_city,container,false )


        return mBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        city = arguments!!.getParcelable("data")!!
        //mBinding= DataBindingUtil.setContentView(this.requireActivity(),R.layout.fragment_user)
        mBinding.city = city

    }


}
