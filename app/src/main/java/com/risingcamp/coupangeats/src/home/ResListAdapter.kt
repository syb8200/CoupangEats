package com.risingcamp.coupangeats.src.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.src.home.models.getResList.ResListResult
import org.jetbrains.anko.find
import java.text.DecimalFormat

class ResListAdapter(var list: List<ResListResult>) : RecyclerView.Adapter<ResListAdapter.AdapterViewHolder>() {
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
            //이미지

            if(item.resImageUrlList.size == 0){
                holder.img_1.visibility = View.GONE
                holder.img_2.visibility = View.GONE
                holder.img_3.visibility = View.GONE
                holder.img_layout.visibility = View.GONE
                holder.img_right_layout.visibility = View.GONE
            }

            if(item.resImageUrlList.size == 1){
                Glide.with(context)
                    .load(item.resImageUrlList[0])
                    .into(holder.img_1)

                holder.img_2.visibility = View.GONE
                holder.img_3.visibility = View.GONE
                holder.img_right_layout.visibility = View.GONE
            }

            if(item.resImageUrlList.size == 2){
                Glide.with(context)
                    .load(item.resImageUrlList[0])
                    .into(holder.img_1)

               holder.img_2.visibility = View.GONE
               holder.img_3.visibility = View.GONE
               holder.img_right_layout.visibility = View.GONE
            }

            if(item.resImageUrlList.size == 3){
                Glide.with(context)
                    .load(item.resImageUrlList[0])
                    .into(holder.img_1)

                Glide.with(context)
                    .load(item.resImageUrlList[1])
                    .into(holder.img_2)

                Glide.with(context)
                    .load(item.resImageUrlList[2])
                    .into(holder.img_3)
            }


            //이름
            holder.name.text = item.resName
            //치타
            var cheetah = item.isCheetah
            if(cheetah==1){
                holder.cheetah.setImageResource(R.drawable.ic_item_home_res_cheetah)
            } else{
                holder.cheetah.visibility = View.INVISIBLE
            }
            //시간
            holder.time_1.text = item.deliveryTime.toString()
            holder.time_2.text = (item.deliveryTime+5).toString()
            //holder.time_2.text = item.time_2
            //별점
            holder.starPoint.text = item.starPoint.toString()
            //리뷰수
            var review = item.reviewCount
            var frmt2 = DecimalFormat("#,###")
            holder.review.text = frmt2.format(review)
            //거리
            holder.distance.text = item.distance.toString()
            //배달비
            var delivery = item.minDeliveryFee
            var frmt4 = DecimalFormat("#,###")
            holder.delivery.text = frmt4.format(delivery)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class AdapterViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var img_1 = v.findViewById<ImageView>(R.id.item_home_res_img_1)
        var img_2 = v.findViewById<ImageView>(R.id.item_home_res_img_2)
        var img_3 = v.findViewById<ImageView>(R.id.item_home_res_img_3)
        var img_layout = v.findViewById<LinearLayout>(R.id.item_home_res_img_layout)
        var img_right_layout = v.findViewById<LinearLayout>(R.id.item_home_res_img_right_layout)

        var name = v.findViewById<TextView>(R.id.item_home_res_name)
        var cheetah = v.findViewById<ImageView>(R.id.item_home_res_cheetah)
        var time_1 = v.findViewById<TextView>(R.id.item_home_res_time_1)
        var time_2 = v.findViewById<TextView>(R.id.item_home_res_time_2)

        var starPoint = v.findViewById<TextView>(R.id.item_home_res_rate)
        var review = v.findViewById<TextView>(R.id.item_home_res_comment)
        var distance = v.findViewById<TextView>(R.id.item_home_res_distance)
        var delivery = v.findViewById<TextView>(R.id.item_home_res_delivery_price)
        var pack = v.findViewById<TextView>(R.id.item_home_res_pack)
    }

}