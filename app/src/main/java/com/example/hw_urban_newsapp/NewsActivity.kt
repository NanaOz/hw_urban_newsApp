package com.example.hw_urban_newsapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.hw_urban_newsapp.Models.Constants.BUSINESS
import com.example.hw_urban_newsapp.Models.Constants.ENTERTAINMENT
import com.example.hw_urban_newsapp.Models.Constants.GENERAL
import com.example.hw_urban_newsapp.Models.Constants.HEALTH
import com.example.hw_urban_newsapp.Models.Constants.HOME
import com.example.hw_urban_newsapp.Models.Constants.SCIENCE
import com.example.hw_urban_newsapp.Models.Constants.SPORTS
import com.example.hw_urban_newsapp.Models.Constants.TECHNOLOGY
import com.example.hw_urban_newsapp.Models.Constants.TOTAL_NEWS_TAB
import com.example.hw_urban_newsapp.Models.NewsModel
import com.example.hw_urban_newsapp.adapters.FragmentAdapter
import com.example.hw_urban_newsapp.databinding.ActivityNewsBinding
import com.example.hw_urban_newsapp.helper.NewsViewModel
import com.google.android.material.tabs.TabLayoutMediator


class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var fragmentAdapter: FragmentAdapter
    private val newsCategories = arrayOf(
        HOME, BUSINESS,
        ENTERTAINMENT, SCIENCE,
        SPORTS, TECHNOLOGY, HEALTH
    )
    private var totalRequestCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        if (!isNetworkAvailable(applicationContext)) {
            binding.displayError.text = getString(R.string.internet_warming)
            binding.displayError.visibility = View.VISIBLE
        }

        requestNews(GENERAL, generalNews)
        requestNews(BUSINESS, businessNews)
        requestNews(ENTERTAINMENT, entertainmentNews)
        requestNews(HEALTH, healthNews)
        requestNews(SCIENCE, scienceNews)
        requestNews(SPORTS, sportsNews)
        requestNews(TECHNOLOGY, techNews)

        fragmentAdapter = FragmentAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = fragmentAdapter
        binding.viewPager.visibility = View.GONE

        binding.imageViewFavorite.setOnClickListener {
            intent = Intent(applicationContext, SavedNewsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_exit -> {
                finishAffinity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestNews(newsCategory: String, newsData: MutableList<NewsModel>) {
        viewModel.getNews(category = newsCategory)?.observe(this) {
            newsData.addAll(it)
            totalRequestCount += 1

            if (newsCategory == GENERAL) {
                setViewPager()
            }

            if (totalRequestCount == TOTAL_NEWS_TAB) {
                binding.viewPager.offscreenPageLimit = 7
            }
        }
    }
    private fun setViewPager() {
        if (!apiRequestError) {
            binding.viewPager.visibility = View.VISIBLE
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = newsCategories[position]
            }.attach()
        } else {
            binding.displayError.text = errorMessage
            binding.displayError.visibility = View.VISIBLE
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return networkCapabilities?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            } ?: false
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo?.isConnectedOrConnecting ?: false
        }
    }

    companion object {
        var generalNews: ArrayList<NewsModel> = ArrayList()
        var entertainmentNews: MutableList<NewsModel> = mutableListOf()
        var businessNews: MutableList<NewsModel> = mutableListOf()
        var healthNews: MutableList<NewsModel> = mutableListOf()
        var scienceNews: MutableList<NewsModel> = mutableListOf()
        var sportsNews: MutableList<NewsModel> = mutableListOf()
        var techNews: MutableList<NewsModel> = mutableListOf()
        var apiRequestError = false
        var errorMessage = "error"
    }
}