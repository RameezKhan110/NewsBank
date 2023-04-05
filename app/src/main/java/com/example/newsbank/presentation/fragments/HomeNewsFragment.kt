package com.example.newsbank.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsbank.R
import com.example.newsbank.presentation.adapter.NewsAdapter
import com.example.newsbank.databinding.FragmentHomeNewsBinding
import com.example.newsbank.presentation.viewmodel.NewsViewModel
import com.example.newsbank.presentation.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
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

        activity?.title = "News Feed"

        newsViewModel.getNews("us", 1)

        val newsAdapter = NewsAdapter() { url ->
            sharedViewModel.gerUrl(url)
            findNavController().navigate(R.id.action_homeNewsFragment_to_detailNewsFragment)
            Toast.makeText(requireContext(), url, Toast.LENGTH_SHORT).show()
        }
        binding.newsRecyclerView.adapter = newsAdapter
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            newsViewModel.newList.collect() { news ->
                if (news.loading) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.newsRecyclerView.visibility = View.GONE
                } else if (news.error.isNotBlank()) {
                    binding.progressBar.visibility = View.GONE
                    binding.newsRecyclerView.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.newsRecyclerView.visibility = View.VISIBLE
                    newsAdapter.submitList(news.data)
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}