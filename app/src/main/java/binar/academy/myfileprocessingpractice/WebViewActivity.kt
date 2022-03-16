package binar.academy.myfileprocessingpractice

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import binar.academy.myfileprocessing.utils.Utils
import binar.academy.myfileprocessingpractice.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            webView.webViewClient = WebViewClient()
            webView.settings.setSupportZoom(true)
            webView.settings.javaScriptEnabled = true
            val url = Utils.getPdfUrl()
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$url")
        }
    }
}