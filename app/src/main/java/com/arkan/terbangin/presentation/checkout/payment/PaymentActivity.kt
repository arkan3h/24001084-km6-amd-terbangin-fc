package com.arkan.terbangin.presentation.checkout.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.databinding.ActivityPaymentBinding
import com.arkan.terbangin.presentation.flightdetail.FlightDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PaymentActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    private val binding: ActivityPaymentBinding by lazy {
        ActivityPaymentBinding.inflate(layoutInflater)
    }

    private val viewModel: PaymentViewModel by viewModel {
        parametersOf(intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindView()
        setClickListener()
    }

    private fun bindView() {
        webView = binding.webViewPayment
        webView.loadUrl(viewModel.payment_url!!)
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)

        webView.webViewClient = WebViewClient()
    }

    private fun setClickListener() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        const val PAYMENT_URL = "PAYMENT_URL"

        fun startActivity(
            context: Context,
            url: String,
        ) {
            val intent = Intent(context, FlightDetailActivity::class.java)
            intent.putExtra(PAYMENT_URL, url)
            context.startActivity(intent)
        }
    }
}
