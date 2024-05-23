package com.arkan.terbangin.presentation.splash_screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivitySplashBinding
import com.arkan.terbangin.presentation.main.MainActivity
import com.arkan.terbangin.presentation.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        onBoardingState()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = this.resources.getColor(R.color.md_theme_onPrimaryFixedVariant, theme)
        setupButtonClickListener()
    }

    private fun onBoardingState() {
        if (viewModel.onBoardingState()) {
            navigateToMain()
        }
    }

    private fun setupButtonClickListener() {
        binding.btnNext.setOnClickListener {
            navigateToRegister()
        }
        binding.tvEnterWithoutLogin.setOnClickListener {
            navigateToMain()
        }
    }

    private fun navigateToRegister() {
        startActivity(
            Intent(this, RegisterActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    private fun navigateToMain() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }
}
