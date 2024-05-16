package com.example.hw_urban_newsapp.Models

import com.example.hw_urban_newsapp.Models.Article

data class NewsDataFromJson(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
