package com.risingcamp.coupangeats.src.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.risingcamp.coupangeats.R
import com.risingcamp.coupangeats.src.home.models.getTopBanner.TopBannerResult

class TopBannerAdapter(var models: List<TopBannerResult>, var context: Context) : RecyclerView.Adapter<TopBannerAdapter.AdapterViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_home_top_banner, parent, false)
        return AdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val item = models[position%5]
        //무한스크롤
        Glide.with(context)
            .load(item.eventImageUrl)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    inner class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image : ImageView = view.findViewById(R.id.item_home_top_banner)
    }
}