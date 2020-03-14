package com.example.volley

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.volley.databinding.RowBinding
import com.example.volley.volleyUtils.CityInfo

class CityAdapter (
    private val mValues: List<CityInfo>,
    private val mListener: onListInteraction
    ) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.ViewHolder {

        var binder: RowBinding
        binder= DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.row,parent,false)
        return ViewHolder(binder)
    }

    override fun getItemCount(): Int =mValues.size

    override fun onBindViewHolder(holder: CityAdapter.ViewHolder, position: Int) {
        val item=mValues[position]

        holder.mView.city=item
        holder.mView.executePendingBindings()

        holder.mView.layoutDay1.setOnClickListener{
            mListener?.onListItemInteraction(item)
        }


    }

    public fun  updateData(){
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: RowBinding): RecyclerView.ViewHolder(mView.root){
    }

    interface onListInteraction{
        fun onListItemInteraction(item : CityInfo?)
    }
}