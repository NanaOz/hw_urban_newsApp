package com.example.hw_urban_newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.hw_urban_newsapp.Models.Constants.NEWS_CONTENT
import com.example.hw_urban_newsapp.Models.Constants.NEWS_DESCRIPTION
import com.example.hw_urban_newsapp.Models.Constants.NEWS_IMAGE_URL
import com.example.hw_urban_newsapp.Models.Constants.NEWS_PUBLICATION_TIME
import com.example.hw_urban_newsapp.Models.Constants.NEWS_SOURCE
import com.example.hw_urban_newsapp.Models.Constants.NEWS_TITLE
import com.example.hw_urban_newsapp.Models.Constants.NEWS_URL
import com.example.hw_urban_newsapp.Models.NewsModel
import com.example.hw_urban_newsapp.databinding.ActivityShowBinding
import com.example.hw_urban_newsapp.helper.NewsViewModel
import java.util.ArrayList

class ShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsData: ArrayList<NewsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        newsData = ArrayList(1)
        val newsUrl = intent.getStringExtra(NEWS_URL)
        val newsContent =
            intent.getStringExtra(NEWS_CONTENT) + ". get paid version to hear full news. "

        newsData.add(
            NewsModel(
                intent.getStringExtra(NEWS_TITLE)!!,
                intent.getStringExtra(NEWS_IMAGE_URL),
                intent.getStringExtra(NEWS_DESCRIPTION),
                newsUrl,
                intent.getStringExtra(NEWS_SOURCE),
                intent.getStringExtra(NEWS_PUBLICATION_TIME),
                newsContent
            )
        )
// Webview
        binding.newsWebview.apply {
//            settings.apply {
//                domStorageEnabled = true
//                loadsImagesAutomatically = true
//                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
//                javaScriptEnabled = true
//            }
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
        }

        if (newsUrl != null) {
            binding.newsWebview.loadUrl(newsUrl)
        }

        binding.imageViewFavorite.setOnClickListener {
            intent = Intent(applicationContext, SavedNewsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_show_news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                this.let { viewModel.insertNews(this@ShowActivity, newsData[0]) }
                Toast.makeText(this, "Новость сохранена!", Toast.LENGTH_SHORT)
                    .show()
            }
            R.id.action_exit -> {
                finishAffinity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}