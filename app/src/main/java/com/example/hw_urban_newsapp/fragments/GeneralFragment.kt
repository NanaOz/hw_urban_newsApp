package com.example.hw_urban_newsapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_urban_newsapp.adapters.CarouselAdapter
import com.example.hw_urban_newsapp.Models.Constants.INITIAL_POSITION
import com.example.hw_urban_newsapp.Models.Constants.NEWS_CONTENT
import com.example.hw_urban_newsapp.Models.Constants.NEWS_DESCRIPTION
import com.example.hw_urban_newsapp.Models.Constants.NEWS_IMAGE_URL
import com.example.hw_urban_newsapp.Models.Constants.NEWS_PUBLICATION_TIME
import com.example.hw_urban_newsapp.Models.Constants.NEWS_SOURCE
import com.example.hw_urban_newsapp.Models.Constants.NEWS_TITLE
import com.example.hw_urban_newsapp.Models.Constants.NEWS_URL
import com.example.hw_urban_newsapp.Models.Constants.TOP_HEADLINES_COUNT
import com.example.hw_urban_newsapp.Models.NewsModel
import com.example.hw_urban_newsapp.NewsActivity
import com.example.hw_urban_newsapp.ShowActivity
import com.example.hw_urban_newsapp.adapters.CustomAdapter
import com.example.hw_urban_newsapp.databinding.FragmentGeneralBinding

class GeneralFragment : Fragment() {

    private lateinit var binding: FragmentGeneralBinding

    private lateinit var adapter: CustomAdapter
    private lateinit var newsDataForTopHeadlines: List<NewsModel>
    private lateinit var newsDataForDown: List<NewsModel>
    var position = INITIAL_POSITION

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeneralBinding.inflate(inflater, container, false)
        val view = binding.root
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.layoutManager = layoutManager

        newsDataForTopHeadlines = NewsActivity.generalNews.slice(0 until TOP_HEADLINES_COUNT)
        newsDataForDown = NewsActivity.generalNews.slice(TOP_HEADLINES_COUNT until NewsActivity.generalNews.size - TOP_HEADLINES_COUNT)
        adapter = CustomAdapter(newsDataForDown)
        binding.recyclerView.adapter = adapter

        val carouselAdapter = CarouselAdapter(newsDataForDown)
        binding.carouselRecyclerView.adapter = carouselAdapter

//        // Инициализация MaskableFrameLayout
//        val inflater = LayoutInflater.from(context)
////
//        for (news in newsDataForTopHeadlines) {
//            val itemView = inflater.inflate(R.layout.list_item_for_top_headlines, binding.carouselRecyclerView, false)
////
//            val imageView = itemView.findViewById<ImageView>(R.id.img)
//            Picasso.get()
//                .load(news.image)
//                .fit()
//                .centerCrop()
//                .error(R.drawable.newstest)
//                .into(imageView)
//
//            val newsTitle = itemView.findViewById<TextView>(R.id.headline)
//            newsTitle.text = news.headLine
//
//            itemView.setOnClickListener {
//                val intent = Intent(context, ShowActivity::class.java).apply {
//                    putExtra(NEWS_URL, news.url)
//                    putExtra(NEWS_TITLE, news.headLine)
//                    putExtra(NEWS_IMAGE_URL, news.image)
//                    putExtra(NEWS_DESCRIPTION, news.description)
//                    putExtra(NEWS_SOURCE, news.source)
//                    putExtra(NEWS_PUBLICATION_TIME, news.time)
//                    putExtra(NEWS_CONTENT, news.content)
//                }
//                context?.startActivity(intent)
//            }
//
//            binding.carouselRecyclerView.addView(itemView)
//        }

        // listitem onClick
        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, ShowActivity::class.java).apply {
                    putExtra(NEWS_URL, newsDataForDown[position].url)
                    putExtra(NEWS_TITLE, newsDataForDown[position].headLine)
                    putExtra(NEWS_IMAGE_URL, newsDataForDown[position].image)
                    putExtra(NEWS_DESCRIPTION, newsDataForDown[position].description)
                    putExtra(NEWS_SOURCE, newsDataForDown[position].source)
                    putExtra(NEWS_PUBLICATION_TIME, newsDataForDown[position].time)
                    putExtra(NEWS_CONTENT, newsDataForDown[position].content)
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