package com.arkan.terbangin.presentation.profile.setting_account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.arkan.terbangin.databinding.ActivitySettingAccountBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingAccountActivity : AppCompatActivity() {
    private val binding: ActivitySettingAccountBinding by lazy {
        ActivitySettingAccountBinding.inflate(layoutInflater)
    }

    private val settingAccountViewModel: SettingAccountViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSwitchListener()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun applyUiMode() {
        val isUsingDarkMode = settingAccountViewModel.isUsingDarkMode()
        AppCompatDelegate.setDefaultNightMode(
            if (isUsingDarkMode) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            },
        )
        binding.swDarkMode.isChecked = isUsingDarkMode
    }

    private fun setSwitchListener() {
        binding.swDarkMode.setOnCheckedChangeListener { btn, isChecked ->
            settingAccountViewModel.setUsingDarkMode(isChecked)
            // applyUiMode()
        }
    }
}
