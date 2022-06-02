package com.risingcamp.coupangeats.src.home.store.storemenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.risingcamp.coupangeats.R

class StoreMenuImgAdapter(var models: ArrayList<String>, var context: Context) : RecyclerView.Adapter<StoreMenuImgAdapter.AdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreMenuImgAdapter.AdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_store_menu_img, parent, false)
        return AdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: StoreMenuImgAdapter.AdapterViewHolder, position: Int) {
        val item = models[position%2]
        //무한스크롤
        Glide.with(context)
            .load(item)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    inner class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image : ImageView = view.findViewById(R.id.item_store_menu_img)
    }
}