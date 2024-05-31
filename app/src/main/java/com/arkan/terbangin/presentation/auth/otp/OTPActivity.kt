package com.arkan.terbangin.presentation.auth.otp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.databinding.ActivityOtpBinding
import com.arkan.terbangin.presentation.auth.login.LoginActivity
import com.arkan.terbangin.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class OTPActivity : AppCompatActivity() {
    private val viewModel: OTPViewModel by viewModel {
        parametersOf(intent.extras)
    }

    private val binding: ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // phoneNumber = intent.getStringExtra("phoneNumber")!!
        addTextChangeListener()
        setClickListener()
        Toast.makeText(
            this,
            "${viewModel.fullName}\n${viewModel.email}\n${viewModel.phoneNumber}\n${viewModel.password}",
            Toast.LENGTH_SHORT,
        ).show()
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
        ) {}

        override fun onTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int,
        ) {}

        override fun afterTextChanged(p0: Editable?) {
            val text = p0.toString()
            when (view) {
                binding.otpEditText1 ->
                    if (text.length == 1) {
                        binding.otpEditText2.requestFocus()
                    } else if (text.isEmpty()) {
                        binding.otpEditText1.requestFocus()
                    }

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

                binding.otpEditText6 ->
                    if (text.length == 1) {
                        trimTypedOTP()
                    } else if (text.isEmpty()) {
                        binding.otpEditText5.requestFocus()
                    }
            }
        }
    }

    private fun trimTypedOTP() {
        val typedOTP = (
            binding.otpEditText1.text.toString() +
                binding.otpEditText2.text.toString() +
                binding.otpEditText3.text.toString() +
                binding.otpEditText4.text.toString() +
                binding.otpEditText5.text.toString() +
                binding.otpEditText6.text.toString()
        )

        if (typedOTP.isNotEmpty()) {
            if (typedOTP.length == 6) {
                verifyOTP(typedOTP)
            } else {
                Toast.makeText(this, "Please Complete OTP", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyOTP(otp: String) {
        viewModel.email?.let { email ->
            Toast.makeText(this, "$email $otp", Toast.LENGTH_SHORT).show()
            viewModel.verifyOTP(email, otp).observe(this) {
                it.proceedWhen(
                    doOnSuccess = {
                        Toast.makeText(this, "OTP Verified", Toast.LENGTH_SHORT).show()
                        navigateToLogin()
                    },
                    doOnError = {
                        Toast.makeText(this, "OTP Failed", Toast.LENGTH_SHORT).show()
                    },
                )
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    companion object {
        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_EMAIL = "EXTRA_EMAIL"
        const val EXTRA_PHONE = "EXTRA_PHONE"
        const val EXTRA_PASSWORD = "EXTRA_PASSWORD"

        fun startActivity(
            context: Context,
            fullName: String,
            email: String,
            phoneNumber: String,
            password: String,
        ) {
            val intent = Intent(context, OTPActivity::class.java)
            intent.putExtra(EXTRA_NAME, fullName)
            intent.putExtra(EXTRA_EMAIL, email)
            intent.putExtra(EXTRA_PHONE, phoneNumber)
            intent.putExtra(EXTRA_PASSWORD, password)
            context.startActivity(intent)
        }
    }
}
