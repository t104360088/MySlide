package com.example.myslider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IntroAdapter(private val item: List<IntroItem>) :
    RecyclerView.Adapter<IntroAdapter.IntroAdapterViewHolder>() {

    class IntroAdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_description = view.findViewById<TextView>(R.id.tv_description)
        val img_icon = view.findViewById<ImageView>(R.id.img_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroAdapterViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_intro, parent, false)

        return IntroAdapterViewHolder(v)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: IntroAdapterViewHolder, position: Int) {
        holder.tv_title.text = item[position].title
        holder.tv_description.text = item[position].description
        holder.img_icon.setImageResource(item[position].icon)
    }
}