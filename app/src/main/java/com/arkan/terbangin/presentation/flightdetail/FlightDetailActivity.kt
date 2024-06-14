package com.arkan.terbangin.presentation.flightdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.databinding.ActivityDetailPenerbanganBinding
import com.arkan.terbangin.presentation.checkout.orderbiodata.OrderBiodataActivity
import com.arkan.terbangin.utils.formatDateHourString
import com.arkan.terbangin.utils.formatDateString
import com.arkan.terbangin.utils.formatHours
import com.arkan.terbangin.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FlightDetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailPenerbanganBinding by lazy {
        ActivityDetailPenerbanganBinding.inflate(layoutInflater)
    }
    private val viewModel: FlightDetailViewModel by viewModel {
        parametersOf(intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setAppBarTitle()
        setClickListener()
        viewModel.getTotalPrice()
        bindView()
    }

    private fun setAppBarTitle() {
        binding.layoutAppBar.tvAppbarTitle.text = getString(R.string.appbar_title_rincian_penerbangan)
    }

    private fun setClickListener() {
        binding.layoutTotalPrice.btnChoose.setOnClickListener {
            if (viewModel.isLoggedIn == null) {
                NonLoginBottomSheet().show(supportFragmentManager, null)
            } else {
//                TicketSoldOutBottomSheet().show(supportFragmentManager, null)
                navigateToOrderBiodata(
                    viewModel.flight!!,
                    viewModel.params!!,
                    viewModel.totalPrice,
                )
            }
        }
        binding.layoutAppBar.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun bindView() {
        binding.tvFlightDestination.text =
            getString(
                R.string.binding_flight_destination_detail,
                viewModel.flight?.startAirportCity,
                viewModel.flight?.endAirportCity,
            )
        binding.tvFlightDuration.text = formatHours(viewModel.flight?.duration!!)
        binding.layoutTotalPrice.tvTotalPrice.text = viewModel.totalPrice.toIndonesianFormat()
        binding.layoutFlightDetail.tvTakeoffTime.text = formatDateHourString(viewModel.flight?.departureAt!!)
        binding.layoutFlightDetail.tvTakeoffDate.text = formatDateString(viewModel.flight?.departureAt!!)
        binding.layoutFlightDetail.tvAirportOrigin.text =
            getString(
                R.string.binding_airport_origin_detail,
                viewModel.flight?.startAirportName,
                viewModel.flight?.startAirportTerminal,
            )
        binding.layoutFlightDetail.tvAirlineName.text =
            getString(
                R.string.binding_airline_name_detail,
                viewModel.flight?.airlineName,
                viewModel.params?.ticketClass?.name,
            )
        binding.layoutFlightDetail.tvAirlineCode.text = viewModel.flight?.airlineSerialNumber
        binding.layoutFlightDetail.tvBaggageCapacity.text =
            getString(
                R.string.binding_baggage_capacity_detail,
                viewModel.flight?.airlineBaggage.toString(),
            )
        binding.layoutFlightDetail.tvCabinBaggageCapacity.text =
            getString(
                R.string.binding_cabin_baggage_capacity_detail,
                viewModel.flight?.airlineCabinBaggage.toString(),
            )
        binding.layoutFlightDetail.tvMoreService.text = viewModel.flight?.airlineAdditionals
        binding.layoutFlightDetail.tvLandingTime.text = formatDateHourString(viewModel.flight?.arrivalAt!!)
        binding.layoutFlightDetail.tvLandingDate.text = formatDateString(viewModel.flight?.arrivalAt!!)
        binding.layoutFlightDetail.tvAirportDestination.text =
            getString(
                R.string.binding_airport_destination_origin,
                viewModel.flight?.endAirportName,
                viewModel.flight?.endAirportTerminal,
            )
    }

    private fun navigateToOrderBiodata(
        item: Flight,
        extras: FlightSearchParams,
        totalPrice: Double,
    ) {
        OrderBiodataActivity.startActivity(
            this,
            extras,
            item,
            totalPrice,
        )
    }

    companion object {
        const val EXTRA_FLIGHT_SEARCH_PARAMS = "EXTRA_FLIGHT_SEARCH_PARAMS"
        const val EXTRA_FLIGHT = "EXTRA_FLIGHT"

        fun startActivity(
            context: Context,
            params: FlightSearchParams,
            flight: Flight,
        ) {
            val intent = Intent(context, FlightDetailActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, params)
            intent.putExtra(EXTRA_FLIGHT, flight)
            context.startActivity(intent)
        }
    }
}
