package com.risingcamp.coupangeats.src.home.store.storemenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.src.home.store.storemenu.models.OptionInfo



class StoreMenuOption1Adapter(var list: List<OptionInfo>) : RecyclerView.Adapter<StoreMenuOption1Adapter.AdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreMenuOption1Adapter.AdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_store_menu_list_radio, parent, false)
        return AdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: StoreMenuOption1Adapter.AdapterViewHolder, position: Int) {
        val item = list[position]

        holder.itemView.apply{
            holder.radio_btn.text = item.optionName

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class AdapterViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var radio_btn = v.findViewById<RadioButton>(R.id.item_store_menu_list_radio_btn)

    }


}