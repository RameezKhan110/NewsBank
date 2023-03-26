package com.example.newsbank.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
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
import com.example.newsbank.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeNewsFragment : Fragment() {

    lateinit var binding: FragmentHomeNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeNewsBinding.inflate(layoutInflater, container, false)

        newsViewModel.getNews()

        val newsAdapter = NewsAdapter() { url ->
            sharedViewModel.gerUrl(url)
            findNavController().navigate(R.id.action_homeNewsFragment_to_detailNewsFragment)
            Toast.makeText(requireContext(), url, Toast.LENGTH_SHORT).show()
        }
        binding.newsRecyclerView.adapter = newsAdapter
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        newsViewModel.liveNewsList.observe(viewLifecycleOwner, Observer { news ->
            newsAdapter.submitList(news.articles)
        })

        return binding.root
    }
}