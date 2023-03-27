package com.example.newsbank.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.newsbank.R
import com.example.newsbank.databinding.FragmentDetailNewsBinding
import com.example.newsbank.presentation.viewmodel.SharedViewModel


class DetailNewsFragment : Fragment() {

    lateinit var binding: FragmentDetailNewsBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailNewsBinding.inflate(layoutInflater, container, false)

        sharedViewModel.liveUrl.observe(viewLifecycleOwner, Observer { url ->
            binding.DetailWebView.apply {
                loadUrl(url)
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding == null
    }
}