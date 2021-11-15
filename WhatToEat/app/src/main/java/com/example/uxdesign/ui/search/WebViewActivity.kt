package com.example.uxdesign.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.uxdesign.R
import com.example.uxdesign.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initWebView()
    }

    private fun initWebView(){
        val url = intent.getStringExtra("url")
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.builtInZoomControls = true
            settings.defaultTextEncodingName = "utf-8"
            loadUrl(url!!)
        }
    }
}