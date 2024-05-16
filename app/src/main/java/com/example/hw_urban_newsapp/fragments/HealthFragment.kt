package com.example.hw_urban_newsapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_urban_newsapp.Models.Constants.NEWS_CONTENT
import com.example.hw_urban_newsapp.Models.Constants.NEWS_DESCRIPTION
import com.example.hw_urban_newsapp.Models.Constants.NEWS_IMAGE_URL
import com.example.hw_urban_newsapp.Models.Constants.NEWS_PUBLICATION_TIME
import com.example.hw_urban_newsapp.Models.Constants.NEWS_SOURCE
import com.example.hw_urban_newsapp.Models.Constants.NEWS_TITLE
import com.example.hw_urban_newsapp.Models.Constants.NEWS_URL
import com.example.hw_urban_newsapp.Models.NewsModel
import com.example.hw_urban_newsapp.NewsActivity
import com.example.hw_urban_newsapp.ShowActivity
import com.example.hw_urban_newsapp.adapters.CustomAdapter
import com.example.hw_urban_newsapp.databinding.FragmentHealthBinding

class HealthFragment : Fragment() {

    private lateinit var binding: FragmentHealthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHealthBinding.inflate(inflater, container, false)
        val view = binding.root
        val newsData: MutableList<NewsModel> = NewsActivity.healthNews
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = CustomAdapter(newsData)
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

                val intent = Intent(context, ShowActivity::class.java).apply {
                    putExtra(NEWS_URL, newsData[position].url)
                    putExtra(NEWS_TITLE, newsData[position].headLine)
                    putExtra(NEWS_IMAGE_URL, newsData[position].image)
                    putExtra(NEWS_DESCRIPTION, newsData[position].description)
                    putExtra(NEWS_SOURCE, newsData[position].source)
                    putExtra(NEWS_PUBLICATION_TIME, newsData[position].time)
                    putExtra(NEWS_CONTENT, newsData[position].content)
                }

                startActivity(intent)
            }
        })

        adapter.setOnItemLongClickListener(object : CustomAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) = Unit
        })

        return view
    }
}