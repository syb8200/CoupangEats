package com.risingcamp.coupangeats.src.home.store.storemenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.src.home.store.storemenu.models.OptionInfo

class StoreMenuOption2Adapter(var list: List<OptionInfo>) : RecyclerView.Adapter<StoreMenuOption2Adapter.AdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreMenuOption2Adapter.AdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_store_menu_list_checkbox, parent, false)
        return AdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: StoreMenuOption2Adapter.AdapterViewHolder, position: Int) {
        val item = list[position]

        holder.itemView.apply{
            holder.checkbox_btn.text = item.optionName

            if(item.optionPrice == 0){
                holder.option_price_layout.visibility = View.INVISIBLE
            } else{
                holder.option_price_layout.visibility = View.VISIBLE
                holder.option_price.text = item.optionPrice.toString()
            }

            holder.checkbox_btn.setOnClickListener {

            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class AdapterViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var checkbox_btn = v.findViewById<CheckBox>(R.id.item_store_menu_list_checkbox_btn)
        var option_price = v.findViewById<TextView>(R.id.item_store_menu_list_option_price)
        var option_price_layout = v.findViewById<LinearLayout>(R.id.item_store_menu_list_option_price_layout)

    }


}