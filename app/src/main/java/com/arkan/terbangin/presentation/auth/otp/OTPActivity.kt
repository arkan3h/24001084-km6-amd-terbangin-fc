package com.arkan.terbangin.presentation.auth.otp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.arkan.terbangin.R
import com.arkan.terbangin.databinding.ActivityOtpBinding
import com.arkan.terbangin.utils.navigateToMain
import com.arkan.terbangin.utils.proceedWhen
import com.arkan.terbangin.utils.showAlertDialog
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

        countDownOtp()
        setClickListener()
        setOtpListener()
    }

    private fun countDownOtp() {
        binding.btnVerifyOtp.isVisible = false
        countDownTimer =
            object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsRemaining = millisUntilFinished / 1000
                    binding.tvResendOtp.text =
                        getString(R.string.text_restart_otp, secondsRemaining.toString())
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

    private fun setOtpListener() {
        binding.otpView.setOtpCompletionListener { otp ->
            verifyOTP(otp)
        }
    }

    private fun verifyOTP(otp: String) {
        viewModel.email?.let { email ->
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
                    proceedLogin(email, password)
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

    private fun proceedLogin(
        email: String,
        password: String,
    ) {
        viewModel.doLogin(email, password).observe(this) { it ->
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
