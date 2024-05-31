package com.arkan.terbangin.presentation.auth.otp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivityOtpBinding
import com.arkan.terbangin.presentation.main.MainActivity
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

    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // phoneNumber = intent.getStringExtra("phoneNumber")!!
        countDownOtp()
        addTextChangeListener()
        setClickListener()
        Log.d("isi register", "${viewModel.fullName}\\n${viewModel.email}\\n${viewModel.phoneNumber}\\n${viewModel.password}")
    }

    private fun countDownOtp() {
        binding.btnVerifyOtp.isVisible = false
        countDownTimer =
            object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsRemaining = millisUntilFinished / 1000
                    binding.tvResendOtp.text =
                        getString(R.string.text_restart_otp, secondsRemaining)
                }

                override fun onFinish() {
                    binding.btnVerifyOtp.isVisible = true
                }
            }.start()
    }

    private fun setClickListener() {
        binding.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnVerifyOtp.setOnClickListener {
            requestOTP()
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
            Log.d("verify otp", "$email, $otp")
            viewModel.verifyOTP(email, otp).observe(this) { it ->
                it.proceedWhen(
                    doOnSuccess = {
                        binding.layoutState.pbLoading.isVisible = false
                        Toast.makeText(this, "OTP Verified", Toast.LENGTH_SHORT).show()
                        proceedRegister(viewModel.fullName!!, viewModel.email!!, viewModel.phoneNumber!!, viewModel.password!!)
                    },
                    doOnError = {
                        binding.layoutState.pbLoading.isVisible = false
                        showAlertDialog(it.exception?.message.orEmpty())
                    },
                    doOnLoading = {
                        binding.layoutState.pbLoading.isVisible = true
                        binding.btnVerifyOtp.isVisible = false
                    },
                )
            }
        }
    }

    private fun proceedRegister(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
    ) {
        viewModel.doRegister(fullName, email, phoneNumber, password).observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.pbLoading.isVisible = false
                    navigateToMain()
                },
                doOnError = {
                    binding.layoutState.pbLoading.isVisible = false
                    showAlertDialog(it.exception?.message.orEmpty())
                },
                doOnLoading = {
                    binding.layoutState.pbLoading.isVisible = true
                    binding.btnVerifyOtp.isVisible = false
                },
            )
        }
    }

    private fun requestOTP() {
        viewModel.email?.let { email ->
            viewModel.requestOTP(email).observe(this) { it ->
                it.proceedWhen(
                    doOnSuccess = {
                        binding.layoutState.pbLoading.isVisible = false
                        binding.btnVerifyOtp.isVisible = true
                        Toast.makeText(this, "Kode OTP Telah Dikirimkan ke Email Anda", Toast.LENGTH_SHORT).show()
                        countDownOtp()
                    },
                    doOnError = {
                        binding.layoutState.pbLoading.isVisible = false
                        binding.btnVerifyOtp.isVisible = true
                        showAlertDialog(it.exception?.message.orEmpty())
                    },
                    doOnLoading = {
                        binding.layoutState.pbLoading.isVisible = true
                        binding.btnVerifyOtp.isVisible = false
                    },
                )
            }
        }
    }

    private fun navigateToMain() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
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
