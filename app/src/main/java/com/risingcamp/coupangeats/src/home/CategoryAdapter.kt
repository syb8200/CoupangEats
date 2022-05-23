package com.risingcamp.coupangeats.src.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.risingcamp.coupangeats.R

class CategoryAdapter(var list: ArrayList<categoryList>) : RecyclerView.Adapter<CategoryAdapter.AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.AdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_home_category, parent, false)
        return AdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.AdapterViewHolder, position: Int) {
        val item = list[position]
        holder.image.setBackgroundResource(item.categoryImg)
        holder.text.text = item.categoryTxt
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image : ImageView = view.findViewById(R.id.item_home_category_img)
        val text : TextView = view.findViewById(R.id.item_home_category_txt)
    }


}