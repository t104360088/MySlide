package com.example.myslider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class SlideAdapter(private val item: List<SlideItem>,
                   private val pager: ViewPager2) :
    RecyclerView.Adapter<SlideAdapter.SliderViewHolder>() {

    class SliderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgView = view.findViewById<ImageView>(R.id.img_slide)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.slide_item_container, parent, false)

        return SliderViewHolder(v)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.imgView.setImageResource(item[position].image)
    }
}