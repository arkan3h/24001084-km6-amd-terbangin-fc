package com.arkan.terbangin.presentation.checkout.passengerbiodata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.arkan.terbangin.R
import com.arkan.terbangin.base.BaseActivity
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.PassengerBioData
import com.arkan.terbangin.data.model.PassengerBioDataList
import com.arkan.terbangin.databinding.ActivityPassengerBiodataBinding
import com.arkan.terbangin.presentation.checkout.passengerbiodata.adapter.PassengerBioDataAdapter
import com.arkan.terbangin.presentation.checkout.selectpassengerseat.SelectPassengerSeatActivity
import com.arkan.terbangin.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PassengerBioDataActivity : BaseActivity() {
    private val binding: ActivityPassengerBiodataBinding by lazy {
        ActivityPassengerBiodataBinding.inflate(layoutInflater)
    }

    private val viewModel: PassengerBioDataViewModel by viewModel {
        parametersOf(intent.extras)
    }

    private var adapter: PassengerBioDataAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setAppBarTitle()
        setForm()
    }

    private fun setAppBarTitle() {
        binding.layoutAppBar.tvAppbarTitle.text = getString(R.string.text_passenger_biodata)
    }

    private fun setClickListener(adapter: PassengerBioDataAdapter) {
        binding.layoutAppBar.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSave.setOnClickListener {
            adapter.getAllData().forEach {
                createPassenger(it)
                Log.d("PassengerData", it.toString())
            }
            viewModel.createdPassengers.observe(this) {
                Log.d("Passenger", "setClickListener: $it")
                navigateToSelectSeat(
                    viewModel.flight!!,
                    viewModel.flightReturn,
                    viewModel.params!!,
                    viewModel.totalPrice!!,
                    PassengerBioDataList(it),
                )
            }
        }
    }

    private fun createPassenger(passenger: PassengerBioData) {
        viewModel.createPassenger(passenger).observe(this) { it ->
            it.proceedWhen(
                doOnLoading = {
                },
                doOnSuccess = {
                    viewModel.passengersList.add(it.payload!!)
                    viewModel.setPassenger()
                },
                doOnError = {
                    it.exception?.let { e -> handleError(e) }
                },
            )
        }
    }

    private fun setForm() {
        adapter =
            PassengerBioDataAdapter(
                viewModel.params!!,
                viewModel.userId!!,
                binding,
            )
        binding.rvItemDataPassengerBiodata.adapter = this.adapter
        setClickListener(adapter!!)
    }

    private fun navigateToSelectSeat(
        flight: Flight,
        flightReturn: Flight?,
        extras: FlightSearchParams,
        totalPrice: Double,
        passengerList: PassengerBioDataList,
    ) {
        SelectPassengerSeatActivity.startActivity(
            this,
            extras,
            flight,
            flightReturn,
            totalPrice,
            passengerList,
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
            val intent = Intent(context, PassengerBioDataActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, params)
            intent.putExtra(EXTRA_FLIGHT, flight)
            intent.putExtra(EXTRA_FLIGHT_RETURN, flightReturn)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            context.startActivity(intent)
        }
    }
}
