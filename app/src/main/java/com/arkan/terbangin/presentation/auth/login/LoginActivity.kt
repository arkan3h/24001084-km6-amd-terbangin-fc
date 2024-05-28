package com.arkan.terbangin.presentation.auth.login

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivityLoginBinding
import com.arkan.terbangin.presentation.main.MainActivity
import com.arkan.terbangin.presentation.auth.register.RegisterActivity
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
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
        binding.btnLogin.setOnClickListener {
            doLogin()
        }
        binding.tvNavToRegister.setOnClickListener {
            navigateToRegister()
        }
        binding.tvForgotPassword.setOnClickListener {
            resetPasswordDialog()
        }
    }

    private fun doLogin() {
        if (isFormValid()) {
            val email = binding.tiEtEmailPhoneNumber.text.toString().trim()
            val password = binding.tiEtPassword.text.toString().trim()
//            loginViewModel.doLogin(email, password)
            navigateToMain()
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

    private fun resetPasswordDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_reset_password)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val backBtn: Button = dialog.findViewById(R.id.btn_back_reset_password_dialog)
        backBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun navigateToRegister() {
        startActivity(
            Intent(this, RegisterActivity::class.java).apply {
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
