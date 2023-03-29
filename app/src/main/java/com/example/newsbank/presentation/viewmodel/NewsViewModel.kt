package com.example.newsbank.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsbank.data.model.Article
import com.example.newsbank.data.model.NewsArticle
import com.example.newsbank.data.repository.NewsRepositoryImpl
import com.example.newsbank.domain.model.News
import com.example.newsbank.domain.use_case.GetNewsUseCase
import com.example.newsbank.presentation.NewsState
import com.example.newsbank.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {

//    private val newsList: MutableLiveData<Resource<NewsArticle>> = MutableLiveData()
//    val liveNewsList: LiveData<Resource<NewsArticle>> = newsList

    private val _newsList = MutableStateFlow<NewsState>(NewsState())
    val newList: StateFlow<NewsState> = _newsList


    fun getNews(country: String, page: Int) {
        getNewsUseCase(country, page).onEach {
            when (it) {
                is Resource.Loading -> {
                    _newsList.value = NewsState(loading = true)
                }
                is Resource.Error -> {
                    _newsList.value = NewsState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _newsList.value = NewsState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

}