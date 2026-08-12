package com.lixiang.remotedisplay

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var urlField: EditText

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        urlField = findViewById(R.id.url_field)
        val openBtn: Button = findViewById(R.id.open_btn)

        webView.webChromeClient = WebChromeClient()
        val ws: WebSettings = webView.settings
        ws.javaScriptEnabled = true
        ws.loadWithOverviewMode = true
        ws.useWideViewPort = true

        // Load a default YouTube embed for demo (Night drive scenic video)
        val defaultEmbed = "https://www.youtube.com/embed/0N6b5FQK0Y4?autoplay=0&rel=0"
        webView.loadUrl(defaultEmbed)

        openBtn.setOnClickListener {
            val text = urlField.text.toString()
            if (text.isNotBlank()) {
                // If user pasted a full video URL, convert to embed
                val embed = toEmbedUrl(text)
                webView.loadUrl(embed)
            }
        }
    }

    private fun toEmbedUrl(input: String): String {
        // Very simple conversion for typical YouTube URLs
        return when {
            input.contains("youtube.com/watch?v=") -> {
                val id = input.substringAfter("v=").substringBefore('&')
                "https://www.youtube.com/embed/$id?autoplay=0&rel=0"
            }
            input.contains("youtu.be/") -> {
                val id = input.substringAfter("youtu.be/").substringBefore('?')
                "https://www.youtube.com/embed/$id?autoplay=0&rel=0"
            }
            input.contains("/embed/") -> input
            else -> input
        }
    }
}
