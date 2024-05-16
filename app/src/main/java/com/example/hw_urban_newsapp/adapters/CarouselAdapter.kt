package com.example.hw_urban_newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_urban_newsapp.Models.NewsModel
import com.example.hw_urban_newsapp.R
import com.squareup.picasso.Picasso


class CarouselAdapter(private val newsList: List<NewsModel>) :
    RecyclerView.Adapter<CarouselAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.img)
        private val headlineTextView: TextView = itemView.findViewById(R.id.headline)

        fun bind(news: NewsModel) {
            Picasso.get()
                .load(news.image)
                .fit()
                .centerCrop()
                .error(R.drawable.newstest)
                .into(imageView)

            headlineTextView.text = news.headLine
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_for_top_headlines, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}