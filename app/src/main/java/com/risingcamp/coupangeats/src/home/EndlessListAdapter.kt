package com.risingcamp.coupangeats.src.home

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.risingcamp.coupangeats.R

class EndlessListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class LoadingViewHolder(v: View) : RecyclerView.ViewHolder(v){
        
    }


    inner class EndlessListViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var img_1 = v.findViewById<ImageView>(R.id.item_home_res_img_1)
        var img_2 = v.findViewById<ImageView>(R.id.item_home_res_img_2)
        var img_3 = v.findViewById<ImageView>(R.id.item_home_res_img_3)

        var name = v.findViewById<TextView>(R.id.item_home_res_name)
        var cheetah = v.findViewById<ImageView>(R.id.item_home_res_cheetah)
        var time_1 = v.findViewById<TextView>(R.id.item_home_res_time_1)
        var time_2 = v.findViewById<TextView>(R.id.item_home_res_time_2)

        var rate = v.findViewById<TextView>(R.id.item_home_res_rate)
        var comment = v.findViewById<TextView>(R.id.item_home_res_comment)
        var distance = v.findViewById<TextView>(R.id.item_home_res_distance)
        var delivery = v.findViewById<TextView>(R.id.item_home_res_delivery_price)
        var pack = v.findViewById<TextView>(R.id.item_home_res_pack)


    }

}