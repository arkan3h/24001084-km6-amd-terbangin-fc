package com.arkan.terbangin.presentation.biodata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.Profile
import com.arkan.terbangin.databinding.ActivityOrderBiodataBinding
import com.arkan.terbangin.utils.proceedWhen
import com.arkan.terbangin.utils.showAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class OrderBiodataActivity : AppCompatActivity() {
    private val binding: ActivityOrderBiodataBinding by lazy {
        ActivityOrderBiodataBinding.inflate(layoutInflater)
    }
    private val viewModel: OrderBiodataViewModel by viewModel {
        parametersOf(intent.extras)
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
        setFamilyName()
        viewModel.getUserID()?.let { uid -> getProfile(uid) }
    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSave.setOnClickListener {
            // navigateToOrderPassenger
        }
    }

    private fun setFamilyName() {
        binding.switchFamily.setOnCheckedChangeListener { _, isChecked ->
            binding.tvFamilyNameLabel.isVisible = isChecked
            binding.etFamilyName.isVisible = isChecked
        }
    }

    private fun getProfile(id: String) {
        viewModel.getProfile(id).observe(this) { it ->
            it.proceedWhen(
                doOnLoading = {
//                    binding.layoutState.pbLoading.isVisible = true
//                    binding.layoutState.tvError.isVisible = false
//                    binding.profileContent.isVisible = true
                },
                doOnSuccess = {
//                    binding.layoutState.pbLoading.isVisible = false
//                    binding.layoutState.tvError.isVisible = false
                    it.payload?.let { data ->
                        bindProfileData(data)
                    }
                },
                doOnError = {
//                    binding.layoutState.pbLoading.isVisible = false
//                    binding.layoutState.tvError.isVisible = true
                    showAlertDialog(it.exception?.message.orEmpty())
                },
            )
        }
    }

    private fun bindProfileData(profile: Profile) {
        profile.let {
            // binding.ivProfileImage.load(it.picture) {
            //    crossfade(true)
            // }
            binding.etFullName.setText(it.fullName)
            binding.etEmail.setText(it.email)
            binding.etPhone.setText(it.phoneNumber)
        }
    }

    companion object {
        const val EXTRA_FLIGHT_SEARCH_PARAMS = "EXTRA_FLIGHT_SEARCH_PARAMS"
        const val EXTRA_FLIGHT = "EXTRA_FLIGHT"
        const val EXTRA_TOTAL_PRICE = "EXTRA_TOTAL_PRICE"

        fun startActivity(
            context: Context,
            params: FlightSearchParams,
            flight: Flight,
            totalPrice: Double,
        ) {
            val intent = Intent(context, OrderBiodataActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, params)
            intent.putExtra(EXTRA_FLIGHT, flight)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            context.startActivity(intent)
        }
    }
}
