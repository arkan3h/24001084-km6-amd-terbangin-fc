package com.arkan.terbangin.presentation.checkout.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivityCheckoutDetailBinding
import com.arkan.terbangin.presentation.checkout.payment.PaymentActivity

class CheckoutDetailActivity : AppCompatActivity() {
    private val binding: ActivityCheckoutDetailBinding by lazy {
        ActivityCheckoutDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setAppBarTitle()
        setClickListener()
    }

    private fun setAppBarTitle() {
        binding.layoutAppBar.tvAppbarTitle.text = getString(R.string.appbar_title_rincian_penerbangan)
    }

    private fun setClickListener() {
        binding.layoutAppBar.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.layoutTotalPrice.btnContinuePayment.setOnClickListener {
            navigateToPayment()
        }
    }

    private fun navigateToPayment() {
        PaymentActivity.startActivity(this, "https://www.google.com")
    }
}
