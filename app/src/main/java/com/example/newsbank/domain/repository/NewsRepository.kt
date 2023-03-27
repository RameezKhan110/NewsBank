package com.example.newsbank.domain.repository

import com.example.newsbank.data.model.NewsArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

interface NewsRepository {

    suspend fun getNews(): Flow<Response<NewsArticle>>
}