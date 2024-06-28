package com.arkan.terbangin.presentation.checkout.payment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.databinding.ActivityPaymentBinding
import com.arkan.terbangin.utils.navigateToMain
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PaymentActivity : AppCompatActivity() {
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun bindView() {
        val webView: WebView = binding.webViewPayment
        webView.webViewClient = WebViewClient()

        webView.apply {
            settings.setSupportZoom(true)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadsImagesAutomatically = true
            loadUrl(viewModel.paymentUrl!!)
        }
    }

    private fun setClickListener() {
        binding.backButton.setOnClickListener {
            navigateToMain()
        }
    }

    companion object {
        const val PAYMENT_URL = "PAYMENT_URL"

        fun startActivity(
            context: Context,
            url: String,
        ) {
            Toast.makeText(context, "Payment URL: $url", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra(PAYMENT_URL, url)
            context.startActivity(intent)
        }
    }
}
