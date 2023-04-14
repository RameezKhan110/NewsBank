package com.example.newsbank.data.model

import com.example.newsbank.domain.model.News

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)

fun Article.toDomainNews(): News {
    return News(
        author = this.author?: "",
        title = this.title?: "",
        image = this.urlToImage?: "",
        content = this.content?: "",
        publishedAt = this.publishedAt?: "",
        url = this.url?: ""
    )
}