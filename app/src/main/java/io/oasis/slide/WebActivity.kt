package io.oasis.slide

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web.*
import android.webkit.WebView



class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val intent = getIntent()
        webView.loadUrl(intent.getStringExtra("link"))
        webView.setWebViewClient(object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                progressBar.visibility = View.GONE
            }
        })

    }
}
