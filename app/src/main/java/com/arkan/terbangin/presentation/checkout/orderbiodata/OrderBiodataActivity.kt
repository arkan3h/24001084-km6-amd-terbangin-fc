package com.arkan.terbangin.presentation.checkout.orderbiodata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.arkan.terbangin.R
import com.arkan.terbangin.base.BaseActivity
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.Profile
import com.arkan.terbangin.databinding.ActivityOrderBiodataBinding
import com.arkan.terbangin.presentation.checkout.passengerbiodata.PassengerBioDataActivity
import com.arkan.terbangin.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class OrderBiodataActivity : BaseActivity() {
    private val binding: ActivityOrderBiodataBinding by lazy {
        ActivityOrderBiodataBinding.inflate(layoutInflater)
    }
    private val viewModel: OrderBiodataViewModel by viewModel {
        parametersOf(intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setAppBarTitle()
        setClickListener()
        setFamilyName()
        viewModel.getUserID()?.let { uid -> getProfile(uid) }
    }

    private fun setAppBarTitle() {
        binding.layoutAppBar.tvAppbarTitle.text = getString(R.string.binding_order_biodata)
    }

    private fun setClickListener() {
        binding.layoutAppBar.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSave.setOnClickListener {
            navigateToPassengerBiodata(
                viewModel.flight!!,
                viewModel.flightReturn,
                viewModel.params!!,
                viewModel.totalPrice!!,
            )
        }
    }

    private fun setFamilyName() {
        binding.switchAskNameFamily.setOnCheckedChangeListener { _, isChecked ->
            binding.tvNameFamilyOrderBiodata.isVisible = isChecked
            binding.tilNameFamilyOrderBiodata.isVisible = isChecked
            binding.tiEtNameFamilyOrderBiodata.isVisible = isChecked
        }
    }

    private fun getProfile(id: String) {
        viewModel.getProfile(id).observe(this) { it ->
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    it.payload?.let { data -> bindProfileData(data) }
                },
                doOnError = {
                    it.exception?.let { e -> handleError(e) }
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.text = it.exception?.message
                    binding.layoutState.tvError.isVisible = true
                },
            )
        }
    }

    private fun bindProfileData(profile: Profile) {
        profile.let {
            binding.tiEtNameOrderBiodata.setText(it.fullName)
            binding.tiEtEmailOrderBiodata.setText(it.email)
            binding.tiEtPhoneNumberOrderBiodata.setText(it.phoneNumber)
        }
    }

    private fun navigateToPassengerBiodata(
        flight: Flight,
        flightReturn: Flight?,
        extras: FlightSearchParams,
        totalPrice: Double,
    ) {
        PassengerBioDataActivity.startActivity(
            this,
            extras,
            flight,
            flightReturn,
            totalPrice,
        )
    }

    companion object {
        const val EXTRA_FLIGHT_SEARCH_PARAMS = "EXTRA_FLIGHT_SEARCH_PARAMS"
        const val EXTRA_FLIGHT = "EXTRA_FLIGHT"
        const val EXTRA_FLIGHT_RETURN = "EXTRA_FLIGHT_RETURN"
        const val EXTRA_TOTAL_PRICE = "EXTRA_TOTAL_PRICE"

        fun startActivity(
            context: Context,
            params: FlightSearchParams,
            flight: Flight,
            flightReturn: Flight?,
            totalPrice: Double,
        ) {
            val intent = Intent(context, OrderBiodataActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, params)
            intent.putExtra(EXTRA_FLIGHT, flight)
            intent.putExtra(EXTRA_FLIGHT_RETURN, flightReturn)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            context.startActivity(intent)
        }
    }
}
