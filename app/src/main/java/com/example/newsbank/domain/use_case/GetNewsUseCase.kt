package com.example.newsbank.domain.use_case

import com.example.newsbank.data.model.toDomainNews
import com.example.newsbank.data.repository.NewsRepositoryImpl
import com.example.newsbank.domain.model.News
import com.example.newsbank.domain.repository.NewsRepository
import com.example.newsbank.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    operator fun invoke(country: String, page: Int): Flow<Resource<List<News>>> = flow {
        try {
            emit(Resource.Loading(null))

            val response = newsRepository.getNews(country, page)

            val list = if(response.body()!!.articles.isEmpty()) emptyList<News>() else response.body()!!.articles.map {
                it.toDomainNews()
            }
            emit(Resource.Success(data = list))
            
        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage?: "Unknown Error", null))
        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage?: "Check Your Internet Connection", null))
        }

    }
}