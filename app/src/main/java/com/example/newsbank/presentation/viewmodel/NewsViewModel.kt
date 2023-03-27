package com.example.newsbank.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsbank.data.model.NewsArticle
import com.example.newsbank.data.repository.NewsRepositoryImpl
import com.example.newsbank.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepositoryImpl: NewsRepositoryImpl) : ViewModel() {

    private val newsList: MutableLiveData<Resource<NewsArticle>> = MutableLiveData()
    val liveNewsList: LiveData<Resource<NewsArticle>> = newsList

    fun getNews() = viewModelScope.launch() {
        newsList.postValue(Resource.loading(null))
        val response = newsRepositoryImpl.getNews()
        try {
            response.collect { news ->
                newsList.postValue(Resource.success(news.body()))
            }
        } catch (t: Throwable) {
            newsList.postValue(Resource.error(t.toString(), null))
        }
    }
}