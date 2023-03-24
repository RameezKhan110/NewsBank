package com.example.newsbank.repository

import com.example.newsbank.api.NewsApiService
import com.example.newsbank.model.NewsArticle
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsApiService) {

    suspend fun getNews(): Response<NewsArticle> {
        return newsService.getNews("us", 1)
    }
}