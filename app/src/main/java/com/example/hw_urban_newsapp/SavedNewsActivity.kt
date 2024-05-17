package com.example.hw_urban_newsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_urban_newsapp.Models.Constants.NEWS_CONTENT
import com.example.hw_urban_newsapp.Models.Constants.NEWS_DESCRIPTION
import com.example.hw_urban_newsapp.Models.Constants.NEWS_IMAGE_URL
import com.example.hw_urban_newsapp.Models.Constants.NEWS_PUBLICATION_TIME
import com.example.hw_urban_newsapp.Models.Constants.NEWS_SOURCE
import com.example.hw_urban_newsapp.Models.Constants.NEWS_TITLE
import com.example.hw_urban_newsapp.Models.Constants.NEWS_URL
import com.example.hw_urban_newsapp.Models.NewsModel
import com.example.hw_urban_newsapp.adapters.CustomAdapter
import com.example.hw_urban_newsapp.databinding.ActivitySavedNewsBinding
import com.example.hw_urban_newsapp.helper.NewsViewModel

class SavedNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsData: MutableList<NewsModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        newsData = mutableListOf()

        val adapter = CustomAdapter(newsData)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        // Получаем сохраненные новости
        viewModel.getNewsFromDB(context = applicationContext)?.observe(this) {
            newsData.clear()
            newsData.addAll(it)
            adapter.notifyDataSetChanged()
        }

        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

                val intent = Intent(this@SavedNewsActivity, ShowActivity::class.java).apply {
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
            override fun onItemLongClick(position: Int) {
                // Диалоговое окно удаления сохраненных новостей
                val alertDialog = AlertDialog.Builder(this@SavedNewsActivity).apply {
                    setMessage("Delete news?")
                    setTitle("Removal")
                    setCancelable(false)
                    setPositiveButton(
                        "Yes"
                    ) { _, _ ->
                        this@SavedNewsActivity.let {
                            viewModel.deleteNews(
                                it,
                                news = newsData[position]
                            )
                        }
                        adapter.notifyItemRemoved(position)
                        Toast.makeText(this@SavedNewsActivity, "Deleted!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    setNegativeButton("No") { _, _ ->
                    }
                }.create()

                alertDialog.show()
            }
        })

        binding.recyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            R.id.action_exit -> {
                finishAffinity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}