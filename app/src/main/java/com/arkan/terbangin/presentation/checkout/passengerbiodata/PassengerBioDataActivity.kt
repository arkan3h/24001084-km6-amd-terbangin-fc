package com.arkan.terbangin.presentation.checkout.passengerbiodata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.PassengerBioData
import com.arkan.terbangin.databinding.ActivityPassengerBiodataBinding
import com.arkan.terbangin.presentation.checkout.passengerbiodata.adapter.PassengerBioDataAdapter
import com.arkan.terbangin.utils.proceedWhen
import com.arkan.terbangin.utils.showAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PassengerBioDataActivity : AppCompatActivity() {
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
        binding.btnChoose.setOnClickListener {
            Log.d("PassengerBioDataActivity", "Save button clicked")
            val passengerDataList = adapter.getAllData()
            Log.d("PassengerBioData", passengerDataList.toString())
            passengerDataList.forEach { passengerData ->
                createPassenger(passengerData)
                Log.d("PassengerData", passengerData.toString())
            }
        }
    }

    private fun createPassenger(passenger: PassengerBioData) {
        viewModel.createPassenger(passenger).observe(this) { it ->
            it.proceedWhen(
                doOnLoading = {
                },
                doOnSuccess = {
                    showAlertDialog("Biodata updated successfully")
                },
                doOnError = {
                    showAlertDialog(it.exception?.message.orEmpty())
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
            val intent = Intent(context, PassengerBioDataActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, params)
            intent.putExtra(EXTRA_FLIGHT, flight)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            context.startActivity(intent)
        }
    }
}
