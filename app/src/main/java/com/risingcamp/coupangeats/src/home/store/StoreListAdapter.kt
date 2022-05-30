package com.risingcamp.coupangeats.src.home.store

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.src.home.store.storemenu.StoreMenu
import java.text.DecimalFormat

class StoreListAdapter(var list: ArrayList<StoreList>) : RecyclerView.Adapter<StoreListAdapter.AdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreListAdapter.AdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_store_detail_list, parent, false)
        return AdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: StoreListAdapter.AdapterViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.apply{
            holder.img.setBackgroundResource(item.menu_img)
            holder.name.text = item.menu_name

            var menu_price = item.menu_price
            var frmt = DecimalFormat("#,###")
            holder.price.text = frmt.format(menu_price).toString()

            holder.des.text = item.menu_des

            holder.itemView.setOnClickListener {
                val intent = Intent(context, StoreMenu::class.java)
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