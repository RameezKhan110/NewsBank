package com.example.newsbank.data.repository

import com.example.newsbank.data.api.NewsApiService
import com.example.newsbank.data.model.NewsArticle
import com.example.newsbank.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsApiService: NewsApiService) :
    NewsRepository {

    override suspend fun getNews(): Flow<Response<NewsArticle>> = flow {
        val response = newsApiService.getNews("us", 1)
        emit(response)
    }.flowOn(Dispatchers.IO)

}