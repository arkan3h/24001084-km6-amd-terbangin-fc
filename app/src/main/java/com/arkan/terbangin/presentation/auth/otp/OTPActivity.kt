package com.arkan.terbangin.presentation.auth.otp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.databinding.ActivityOtpBinding

class OTPActivity : AppCompatActivity() {
    private lateinit var phoneNumber: String

    private val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // phoneNumber = intent.getStringExtra("phoneNumber")!!
        addTextChangeListener()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun addTextChangeListener() {
        binding.otpEditText1.addTextChangedListener(EditTextWatcher(binding.otpEditText1))
        binding.otpEditText2.addTextChangedListener(EditTextWatcher(binding.otpEditText2))
        binding.otpEditText3.addTextChangedListener(EditTextWatcher(binding.otpEditText3))
        binding.otpEditText4.addTextChangedListener(EditTextWatcher(binding.otpEditText4))
        binding.otpEditText5.addTextChangedListener(EditTextWatcher(binding.otpEditText5))
        binding.otpEditText6.addTextChangedListener(EditTextWatcher(binding.otpEditText6))
    }

    inner class EditTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int,
        ) {
        }

        override fun onTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int,
        ) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val text = p0.toString()
            when (view) {
                binding.otpEditText1 -> if (text.length == 1) binding.otpEditText2.requestFocus()
                binding.otpEditText2 ->
                    if (text.length == 1) {
                        binding.otpEditText3.requestFocus()
                    } else if (text.isEmpty()) {
                        binding.otpEditText1.requestFocus()
                    }
                binding.otpEditText3 ->
                    if (text.length == 1) {
                        binding.otpEditText4.requestFocus()
                    } else if (text.isEmpty()) {
                        binding.otpEditText2.requestFocus()
                    }
                binding.otpEditText4 ->
                    if (text.length == 1) {
                        binding.otpEditText5.requestFocus()
                    } else if (text.isEmpty()) {
                        binding.otpEditText3.requestFocus()
                    }
                binding.otpEditText5 ->
                    if (text.length == 1) {
                        binding.otpEditText6.requestFocus()
                    } else if (text.isEmpty()) {
                        binding.otpEditText4.requestFocus()
                    }
                binding.otpEditText6 -> if (text.isEmpty()) binding.otpEditText5.requestFocus()
            }
        }
    }
}
