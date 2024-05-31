package com.arkan.terbangin.presentation.auth.reset_password

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivityResetPasswordBinding
import com.arkan.terbangin.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordActivity : AppCompatActivity() {
    private val binding: ActivityResetPasswordBinding by lazy {
        ActivityResetPasswordBinding.inflate(layoutInflater)
    }

    private val viewModel: ResetPasswordViewModel by viewModel()

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
        binding.btnSendEmail.setOnClickListener {
            sendEmail()
        }
        binding.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun sendEmail() {
        if (isFormValid()) {
            val email = binding.tiEtEmail.text.toString().trim()
            proceedSendEmail(email)
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.tiEtEmail.text.toString().trim()
        return checkEmailValidation(email)
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

    private fun proceedSendEmail(email: String) {
        viewModel.doResetPassword(email).observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnSendEmail.isVisible = true
                    resetPasswordDialog()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnSendEmail.isVisible = true
                    Toast.makeText(
                        this,
                        "Login Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnSendEmail.isVisible = false
                },
            )
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
            onBackPressedDispatcher.onBackPressed()
        }
        dialog.show()
    }
}
