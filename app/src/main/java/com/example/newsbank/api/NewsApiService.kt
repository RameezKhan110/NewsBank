package com.example.newsbank.api

import com.example.newsbank.utils.Constants
import com.example.newsbank.model.NewsArticle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {

    @GET("v2/top-headlines?apikey=${Constants.API_KEY}")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("page") page: Int
    ): Response<NewsArticle>
}
