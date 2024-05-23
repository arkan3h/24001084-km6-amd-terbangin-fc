package com.arkan.terbangin.presentation.edit_profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListener()
    }

    private fun setClickListener() {
        binding.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}