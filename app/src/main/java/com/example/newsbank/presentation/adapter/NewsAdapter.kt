package com.example.newsbank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsbank.databinding.NewsItemBinding
import com.example.newsbank.domain.model.News

class NewsAdapter(
    private val onDetailClicked: (String) -> Unit
) :
    ListAdapter<News, NewsAdapter.NewsViewHolder>(DiffUtil()) {

    class NewsViewHolder(binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val allNews = getItem(position)
        NewsItemBinding.bind(holder.itemView).apply() {
            newsImage.load(allNews.image)
            newsTitle.text = if(allNews.title.isNotEmpty()) allNews.title.toString() else "null"
            newsContent.text = if(allNews.content.isNotEmpty()) allNews.content.toString() else "null"
            newsPublisher.text = if (allNews.author!!.isNotEmpty()) allNews.author.toString() else "null"
            newsPublishedDate.text = if(allNews.publishedAt.isNotEmpty()) allNews.publishedAt.toString() else " null"
            newsItem.setOnClickListener {
                onDetailClicked(allNews.url)
            }
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: News, newItem: News
        ): Boolean {
            return oldItem == newItem
        }
    }
}