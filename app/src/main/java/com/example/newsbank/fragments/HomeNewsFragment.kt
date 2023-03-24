package com.example.newsbank.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsbank.R
import com.example.newsbank.adapter.NewsAdapter
import com.example.newsbank.databinding.FragmentHomeNewsBinding
import com.example.newsbank.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeNewsFragment : Fragment() {

    lateinit var binding: FragmentHomeNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeNewsBinding.inflate(layoutInflater, container, false)

        newsViewModel.getNews()

        val newsAdapter = NewsAdapter() { url ->
            findNavController().navigate(R.id.action_homeNewsFragment_to_detailNewsFragment)
        }
        binding.newsRecyclerView.adapter = newsAdapter
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        newsViewModel.liveNewsList.observe(viewLifecycleOwner, Observer {
            newsAdapter.submitList(it.articles)
        })

        return binding.root
    }
}