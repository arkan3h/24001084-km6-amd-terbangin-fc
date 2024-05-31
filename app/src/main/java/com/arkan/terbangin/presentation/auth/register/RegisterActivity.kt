package com.arkan.terbangin.presentation.auth.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivityRegisterBinding
import com.arkan.terbangin.presentation.auth.login.LoginActivity
import com.arkan.terbangin.presentation.auth.otp.OTPActivity
import com.arkan.terbangin.utils.highLightWord
import com.arkan.terbangin.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnRegister.setOnClickListener {
            doRegister()
        }
        binding.tvNavToLogin.highLightWord("Masuk di sini") {
            navigateToLogin()
        }
    }

    private fun doRegister() {
        if (isFormValid()) {
            val email = binding.tiEtEmail.text.toString().trim()
            val password = binding.tiEtMakePassword.text.toString().trim()
            val fullName = binding.tiEtName.text.toString().trim()
            val number = "0${binding.tiPhoneNumber.text.toString().trim()}"
            requestOTP(fullName, email, number, password)
        }
    }

    private fun isFormValid(): Boolean {
        val password = binding.tiEtMakePassword.text.toString().trim()
        val fullName = binding.tiEtName.text.toString().trim()
        val email = binding.tiEtEmail.text.toString().trim()
        val number = binding.tiPhoneNumber.text.toString().trim()

        return checkNameValidation(fullName) && checkEmailValidation(email) &&
            checkNumberValidation(number) &&
            checkPasswordValidation(password, binding.tilMakePassword)
    }

    private fun checkNameValidation(fullName: String): Boolean {
        return if (fullName.isEmpty()) {
            binding.tilName.isErrorEnabled = true
            binding.tilName.error = getString(R.string.text_error_name_empty)
            false
        } else {
            binding.tilName.isErrorEnabled = false
            true
        }
    }

    private fun checkNumberValidation(number: String): Boolean {
        return if (number.isEmpty()) {
            binding.tilPhoneNumber.isErrorEnabled = true
            binding.tilPhoneNumber.error = getString(R.string.text_error_number_empty)
            false
        } else if (!Patterns.PHONE.matcher(number).matches()) {
            binding.tilPhoneNumber.isErrorEnabled = true
            binding.tilPhoneNumber.error = getString(R.string.text_error_number_invalid)
            false
        } else if (!isNumberFormatValid(number)) {
            binding.tilPhoneNumber.isErrorEnabled = true
            binding.tilPhoneNumber.error = getString(R.string.text_error_number_invalid2)
            false
        } else {
            binding.tilPhoneNumber.isErrorEnabled = false
            true
        }
    }

    private fun isNumberFormatValid(number: String): Boolean {
        val reg = Regex("^[1-9]\\d{3,}\$")
        return reg.matches(number)
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(
        confirmPassword: String,
        textInputLayout: TextInputLayout,
    ): Boolean {
        return if (confirmPassword.isEmpty()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_pw_empty)
            false
        } else if (confirmPassword.length < 8) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_pw_lower)
            false
        } else {
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun requestOTP(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ) {
        viewModel.requestOTP(email).observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    navigateToOtp(fullName, email, phoneNumber, password)
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    showAlertDialog(it.exception?.message.orEmpty())
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnRegister.isVisible = false
                },
            )
        }
    }

    private fun navigateToOtp(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ) {
        OTPActivity.startActivity(this, fullName, email, phoneNumber, password)
    }

    private fun showAlertDialog(it: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(it)
        builder.setNegativeButton("Close") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }
}
