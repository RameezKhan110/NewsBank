package com.example.newsbank.presentation

import com.example.newsbank.domain.model.News

data class NewsState(
    val data: List<News>? = null,
    val error: String = " ",
    val loading: Boolean = false
)
