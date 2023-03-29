package com.example.newsbank.domain.model

data class News(
    val author: String? = null,
    val publishedAt: String,
    val title: String,
    val content: String,
    val image: String,
    val url: String
)
