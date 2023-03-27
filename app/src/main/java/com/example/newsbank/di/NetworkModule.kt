package com.example.newsbank.di

import com.example.newsbank.data.api.NewsApiService
import com.example.newsbank.data.repository.NewsRepositoryImpl
import com.example.newsbank.domain.repository.NewsRepository
import com.example.newsbank.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesBaseUrl(): String = Constants.BASE_URL

    @Provides
    @Singleton
    fun providesHttpClient() : OkHttpClient = OkHttpClient
        .Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor(Constants.AUTH_TOKEN))
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun providesNewsApiService(retrofit: Retrofit): NewsApiService = retrofit.create()

    @Provides
    fun providesNewsRepository(newsApiService: NewsApiService): NewsRepository = NewsRepositoryImpl(newsApiService)

}