package com.example.hw_urban_newsapp.fragments

import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_urban_newsapp.MainActivity
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
import com.example.hw_urban_newsapp.R
import com.example.hw_urban_newsapp.ShowActivity
import com.example.hw_urban_newsapp.adapters.CustomAdapter
import com.example.hw_urban_newsapp.databinding.FragmentGeneralBinding
import com.google.android.material.carousel.MaskableFrameLayout
import com.squareup.picasso.Picasso

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


//        carouselView = view.findViewById<MaskableFrameLayout>(R.id.home_carousel)
//
//        carouselView.apply {
//
//            size = newsDataForTopHeadlines.size
//            autoPlay = true
//            indicatorAnimationType = IndicatorAnimationType.THIN_WORM
//            carouselOffset = OffsetType.CENTER
//
//            setCarouselViewListener { view, position ->
//                val imageView = view.findViewById<ImageView>(R.id.img)
//                Picasso.get()
//                    .load(newsDataForTopHeadlines[position].image)
//                    .fit()
//                    .centerCrop()
//                    .error(R.drawable.newstest)
//                    .into(imageView)
//
//
//                val newsTitle = view.findViewById<TextView>(R.id.headline)
//                newsTitle.text = newsDataForTopHeadlines[position].headLine
//
//                view.setOnClickListener {
//
//                    val intent = Intent(context, ShowActivity::class.java).apply {
//                        putExtra(NEWS_URL, newsDataForTopHeadlines[position].url)
//                        putExtra(NEWS_TITLE, newsDataForTopHeadlines[position].headLine)
//                        putExtra(NEWS_IMAGE_URL, newsDataForTopHeadlines[position].image)
//                        putExtra(NEWS_DESCRIPTION, newsDataForTopHeadlines[position].description)
//                        putExtra(NEWS_SOURCE, newsDataForTopHeadlines[position].source)
//                        putExtra(NEWS_PUBLICATION_TIME, newsDataForTopHeadlines[position].time)
//                        putExtra(NEWS_CONTENT, newsDataForTopHeadlines[position].content)
//                    }
//
//                    startActivity(intent)
//
//                }
//            }
//            // After you finish setting up, show the CarouselView
//            show()
//        }

        // Инициализация MaskableFrameLayout
        val inflater = LayoutInflater.from(context)

        for (news in newsDataForTopHeadlines) {
            val itemView = inflater.inflate(R.layout.list_item_for_top_headlines, binding.homeCarousel, false)

            val imageView = itemView.findViewById<ImageView>(R.id.img)
            Picasso.get()
                .load(news.image)
                .fit()
                .centerCrop()
                .error(R.drawable.newstest)
                .into(imageView)

            val newsTitle = itemView.findViewById<TextView>(R.id.headline)
            newsTitle.text = news.headLine

            itemView.setOnClickListener {
                val intent = Intent(context, ShowActivity::class.java).apply {
                    putExtra(NEWS_URL, news.url)
                    putExtra(NEWS_TITLE, news.headLine)
                    putExtra(NEWS_IMAGE_URL, news.image)
                    putExtra(NEWS_DESCRIPTION, news.description)
                    putExtra(NEWS_SOURCE, news.source)
                    putExtra(NEWS_PUBLICATION_TIME, news.time)
                    putExtra(NEWS_CONTENT, news.content)
                }
                context?.startActivity(intent)
            }

            binding.homeCarousel.addView(itemView)
        }

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