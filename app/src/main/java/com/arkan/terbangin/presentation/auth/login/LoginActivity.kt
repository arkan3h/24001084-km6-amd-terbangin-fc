package com.arkan.terbangin.presentation.auth.login

import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.arkan.terbangin.R
import com.arkan.terbangin.base.BaseActivity
import com.arkan.terbangin.databinding.ActivityLoginBinding
import com.arkan.terbangin.utils.highLightWord
import com.arkan.terbangin.utils.navigateToMain
import com.arkan.terbangin.utils.navigateToRegister
import com.arkan.terbangin.utils.navigateToResetPassword
import com.arkan.terbangin.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModel()

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
        binding.btnLogin.setOnClickListener {
            doLogin()
        }
        binding.tvNavToRegister.highLightWord("Daftar di sini") {
            navigateToRegister()
        }
        binding.tvForgotPassword.setOnClickListener {
            navigateToResetPassword()
        }
    }

    private fun doLogin() {
        if (isFormValid()) {
            val email = binding.tiEtEmailPhoneNumber.text.toString().trim()
            val password = binding.tiEtPassword.text.toString().trim()
            proceedLogin(email, password)
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.tiEtEmailPhoneNumber.text.toString().trim()
        val password = binding.tiEtPassword.text.toString().trim()

        return checkEmailValidation(email) &&
            checkPasswordValidation(password, binding.tilPassword)
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.tilEmailPhoneNumber.isErrorEnabled = true
            binding.tilEmailPhoneNumber.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmailPhoneNumber.isErrorEnabled = true
            binding.tilEmailPhoneNumber.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.tilEmailPhoneNumber.isErrorEnabled = false
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

    private fun proceedLogin(
        email: String,
        password: String,
    ) {
        viewModel.doLogin(email, password).observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    navigateToMain()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isVisible = true
                    it.exception?.let { e -> handleError(e) }
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogin.isVisible = false
                },
            )
        }
    }
}
