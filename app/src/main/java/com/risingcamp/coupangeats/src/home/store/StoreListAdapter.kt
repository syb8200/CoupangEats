package com.risingcamp.coupangeats.src.home.store

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
import com.risingcamp.coupangeats.src.home.store.models.getStoreAllMenu.Result
import com.risingcamp.coupangeats.src.home.store.storemenu.StoreMenuActivity
import java.text.DecimalFormat

class StoreListAdapter(var list: MutableList<Result>) : RecyclerView.Adapter<StoreListAdapter.AdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreListAdapter.AdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_store_detail_list, parent, false)
        return AdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: StoreListAdapter.AdapterViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.apply{

            if(item.menuImageUrl == null){
                holder.img.visibility = View.GONE
            } else{
                Glide.with(context)
                    .load(item.menuImageUrl)
                    .into(holder.img)
            }

            holder.name.text = item.menuName

            var menu_price = item.menuPrice
            var frmt = DecimalFormat("#,###")
            holder.price.text = frmt.format(menu_price).toString()

            if(item.menuDescription == null){
                holder.des.visibility = View.GONE
            } else{
                holder.des.text = item.menuDescription
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(context, StoreMenuActivity::class.java)

                var menu_id = item.menuId
                Log.d("메뉴아이디", "$menu_id")

                intent.putExtra("menuId", menu_id)
                intent.run { context.startActivity(this) }
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class AdapterViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var img = v.findViewById<ImageView>(R.id.item_store_detail_list_img)
        var name = v.findViewById<TextView>(R.id.item_store_detail_menu_name)
        var price = v.findViewById<TextView>(R.id.item_store_detail_menu_price_1)
        var des = v.findViewById<TextView>(R.id.item_store_detail_menu_des)
        var line = v.findViewById<View>(R.id.item_store_detail_line)
    }

}