package com.risingcamp.coupangeats.src.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.risingcamp.coupangeats.R
import java.text.DecimalFormat

class HorizontalResListAdapter(private val list: ArrayList<horizontalResList>) : RecyclerView.Adapter<HorizontalResListAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalResListAdapter.CustomViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_home_horizontal_list, parent, false)
        return CustomViewHolder(v)
    }

    override fun onBindViewHolder(holder: HorizontalResListAdapter.CustomViewHolder, position: Int) {
        val item = list[position]

        holder.img.setBackgroundResource(item.img)
        holder.name.text = item.name
        holder.rate.text = item.rate.toString()
        holder.comment.text = item.comment.toString()
        holder.distance.text = item.distance.toString()
        var delivery = item.delivery
        var frmt = DecimalFormat("#,###")
        holder.delivery.text = frmt.format(delivery).toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CustomViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val img = v.findViewById<ImageView>(R.id.item_home_horizontal_list_img)
        val name = v.findViewById<TextView>(R.id.item_home_horizontal_list_name)
        val rate = v.findViewById<TextView>(R.id.item_home_horizontal_list_rate)
        val comment = v.findViewById<TextView>(R.id.item_home_horizontal_list_comment)
        val distance = v.findViewById<TextView>(R.id.item_home_horizontal_list_distance)
        val delivery = v.findViewById<TextView>(R.id.item_home_horizontal_list_delivery)
    }

}