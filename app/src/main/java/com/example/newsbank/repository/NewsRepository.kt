package com.example.newsbank.repository

import com.example.newsbank.api.NewsApiService
import com.example.newsbank.model.NewsArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApiService: NewsApiService) {

    suspend fun getNews(): Flow<Response<NewsArticle>> = flow {
        val response = newsApiService.getNews("us", 1)
        emit(response)
    }.flowOn(Dispatchers.IO)

}