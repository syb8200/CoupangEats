package com.risingcamp.coupangeats.src.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.risingcamp.coupangeats.R
import java.text.DecimalFormat

class ResListAdapter(var list: ArrayList<ResList>) : RecyclerView.Adapter<ResListAdapter.AdapterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResListAdapter.AdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_home_res_list, parent, false)
        return AdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: ResListAdapter.AdapterViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.apply{
            holder.img_1.setBackgroundResource(item.img1)
            holder.img_2.setBackgroundResource(item.img2)
            holder.img_3.setBackgroundResource(item.img3)
            holder.name.text = item.name
            holder.cheetah.setBackgroundResource(item.cheetah)
            holder.time_1.text = item.time_1
            holder.time_2.text = item.time_2

            //var rate = item.rate
            //var frmt = DecimalFormat("#.#")
            //holder.rate.text = frmt.format(rate)
            holder.rate.text = item.rate.toString()

            var comment = item.comment
            var frmt2 = DecimalFormat("#,###")
            holder.comment.text = frmt2.format(comment)

            //var distance = item.distance
            //var frmt3 = DecimalFormat("#.#")
            //holder.distance.text = frmt3.format(distance)
            holder.distance.text = item.distance.toString()

            var delivery = item.delivery
            var frmt4 = DecimalFormat("#,###")
            holder.delivery.text = frmt4.format(delivery)

            holder.pack.text = item.pack
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class AdapterViewHolder(v: View) : RecyclerView.ViewHolder(v) {
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