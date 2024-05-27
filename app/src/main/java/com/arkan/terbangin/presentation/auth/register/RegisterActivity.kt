package com.arkan.terbangin.presentation.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivityRegisterBinding
import com.arkan.terbangin.presentation.login.LoginActivity
import com.arkan.terbangin.presentation.main.MainActivity
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

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
        binding.tvNavToLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun doRegister() {
        if (isFormValid()) {
            val email = binding.tiEtEmail.text.toString().trim()
            val password = binding.tiEtMakePassword.text.toString().trim()
            val fullName = binding.tiEtName.text.toString().trim()
            val number = binding.tiPhoneNumber.text.toString().trim()
//            proceedRegister(email, password, fullName)
            navigateToMain()
        }
    }

    private fun isFormValid(): Boolean {
        val password = binding.tiEtMakePassword.text.toString().trim()
//        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        val fullName = binding.tiEtName.text.toString().trim()
        val email = binding.tiEtEmail.text.toString().trim()
        val number = binding.tiPhoneNumber.text.toString().trim()

        return checkNameValidation(fullName) && checkEmailValidation(email) &&
            checkNumberValidation(number) &&
            checkPasswordValidation(password, binding.tilMakePassword)
//                && checkPasswordValidation(confirmPassword, binding.tilConfirmPassword)
//                && checkPwdAndConfirmPwd(password, confirmPassword)
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
        } else {
            binding.tilPhoneNumber.isErrorEnabled = false
            true
        }
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

//    private fun checkPwdAndConfirmPwd(
//        password: String,
//        confirmPassword: String,
//    ): Boolean {
//        return if (password != confirmPassword) {
//            binding.tilPassword.isErrorEnabled = true
//            binding.tilPassword.error =
//                getString(R.string.text_pw_nomatch)
//            binding.tilConfirmPassword.isErrorEnabled = true
//            binding.tilConfirmPassword.error =
//                getString(R.string.text_pw_nomatch)
//            false
//        } else {
//            binding.tilPassword.isErrorEnabled = false
//            binding.tilConfirmPassword.isErrorEnabled = false
//            true
//        }
//    }

    private fun navigateToLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
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
