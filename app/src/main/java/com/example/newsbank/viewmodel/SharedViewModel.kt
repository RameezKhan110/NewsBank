package com.example.newsbank.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    private val mutableUrl : MutableLiveData<String> = MutableLiveData()
    val liveUrl : LiveData<String>
    get() = mutableUrl

    fun gerUrl(url : String){
        mutableUrl.value = url
    }
}