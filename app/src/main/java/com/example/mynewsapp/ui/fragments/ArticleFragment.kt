package com.example.mynewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mynewsapp.ui.NewsActivity
import com.example.mynewsapp.ui.NewsViewModel
import com.example.newsprojectpractice.R
import com.example.newsprojectpractice.databinding.FragmentArticleBinding
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var newsViewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article

        // Show ProgressBar, Hide WebView Initially
        binding.progressBar.visibility = View.VISIBLE
        binding.webView.visibility = View.GONE

        binding.webView.apply {
            webViewClient = WebViewClient()
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: android.webkit.WebView?, newProgress: Int) {
                    if (newProgress == 100) {
                        // Hide ProgressBar and Show WebView when fully loaded
                        binding.progressBar.visibility = View.GONE
                        binding.webView.visibility = View.VISIBLE
                    }
                }
            }
            article.url?.let {
                loadUrl(it)
            }
        }

        binding.fab.setOnClickListener {
            newsViewModel.addToFavorites(article)

            // Create Snackbar and Move it to the Top
            val snackbar = Snackbar.make(view, "Added to favorites", Snackbar.LENGTH_SHORT)
            val snackbarView = snackbar.view
            val params = snackbarView.layoutParams as ViewGroup.MarginLayoutParams

            params.setMargins(0, 0, 0, 1450) // Move Snackbar 100dp from the top
            snackbarView.layoutParams = params

            snackbar.show()
        }
    }
}
