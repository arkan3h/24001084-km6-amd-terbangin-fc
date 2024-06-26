package com.arkan.terbangin.presentation.splashscreen

import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivitySplashBinding
import com.arkan.terbangin.utils.navigateToMain
import com.arkan.terbangin.utils.navigateToRegister
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
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = this.resources.getColor(R.color.md_theme_onPrimaryFixedVariant, theme)
        val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        val isDarkTheme = uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES
        ViewCompat.getWindowInsetsController(window.decorView)?.isAppearanceLightStatusBars = isDarkTheme
        window.statusBarColor = this.resources.getColor(R.color.md_theme_primary, theme)
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
}
