package com.risingcamp.coupangeats.src.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.src.home.models.getFranRes.FranResult
import com.risingcamp.coupangeats.src.home.store.StoreActivity
import java.text.DecimalFormat

class HorizontalResListAdapter(val context: Context, var list:List<FranResult>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM = 1
    private val FOOTER = 2

    //private var horizontalResList : ArrayList<HorizontalResList> = arrayListOf()

    private fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         return when(viewType){
            FOOTER -> FooterViewHolder(parent.inflate(R.layout.item_home_horizontal_list_footer))
             else -> CustomViewHolder(parent.inflate(R.layout.item_home_horizontal_list))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is FooterViewHolder -> {
                holder.itemView.setOnClickListener {
                    Log.d("푸터", "푸터 생성 완료")
                }
            }
            is CustomViewHolder -> {
                val item = list[position]

                //내부 데이터를 사용하여 각 아이템 값 설정
                holder.itemView.apply{

                    Glide.with(context)
                        .load(item.resImageUrl)
                        .into(holder.img)

                    holder.name.text = item.resName
                    holder.rate.text = item.starPoint.toString()
                    holder.comment.text = item.reviewCount.toString()
                    holder.distance.text = item.distance.toString()
                    var delivery = item.minDeliveryFee
                    var frmt = DecimalFormat("#,###")
                    holder.delivery.text = frmt.format(delivery).toString()

                    holder.itemView.setOnClickListener {
                        val intent = Intent(context, StoreActivity::class.java)
                        intent.run { context.startActivity(this) }
                    }

                }
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size+1
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            itemCount-1 -> FOOTER
            else -> ITEM
        }
    }


    //Item Type에 따른 ViewHolder Class
    inner class FooterViewHolder(v : View) : RecyclerView.ViewHolder(v){
        var footer_img = v.findViewById<ImageView>(R.id.item_home_horizontal_list_footer_btn)
    }


    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.findViewById<ImageView>(R.id.item_home_horizontal_list_img)
        var name = itemView.findViewById<TextView>(R.id.item_home_horizontal_list_name)
        var rate = itemView.findViewById<TextView>(R.id.item_home_horizontal_list_rate)
        var comment = itemView.findViewById<TextView>(R.id.item_home_horizontal_list_comment)
        var distance = itemView.findViewById<TextView>(R.id.item_home_horizontal_list_distance)
        var delivery = itemView.findViewById<TextView>(R.id.item_home_horizontal_list_delivery)

    }

}