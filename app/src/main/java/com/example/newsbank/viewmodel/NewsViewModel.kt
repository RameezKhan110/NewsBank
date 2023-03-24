package com.example.newsbank.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsbank.model.NewsArticle
import com.example.newsbank.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val mutableNewsList: MutableLiveData<NewsArticle> = MutableLiveData()
    val liveNewsList: LiveData<NewsArticle> = mutableNewsList


    fun getNews() = viewModelScope.launch {
        val news = newsRepository.getNews()
        try {
            mutableNewsList.postValue(news.body())
        } catch (e: Exception) {
            Log.d("TAG", "Error in fetching Data", e)
        }
    }
}