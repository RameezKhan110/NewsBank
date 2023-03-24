package com.example.newsbank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsbank.databinding.NewsItemBinding
import com.example.newsbank.model.Article

class NewsAdapter(
    private val onDetailClicked: (String) -> Unit
) :
    ListAdapter<Article, NewsAdapter.NewsViewHolder>(DiffUtil()) {

    class NewsViewHolder(binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val allNews = getItem(position)
        NewsItemBinding.bind(holder.itemView).apply() {
            Glide.with(holder.itemView).load(allNews.urlToImage).into(newsImage)
            newsTitle.text = allNews.title.toString()
            newsContent.text = if (allNews.content != null) allNews.content.toString() else "null"
            newsPublisher.text = if (allNews.author != null) allNews.author.toString() else "null"
            newsPublishedDate.text = allNews.publishedAt.toString()
            newsItem.setOnClickListener {
                onDetailClicked(allNews.url)
            }
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: Article, newItem: Article
        ): Boolean {
            return oldItem == newItem
        }
    }
}