package com.arkan.terbangin.presentation.flightdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.databinding.ActivityDetailPenerbanganBinding
import com.arkan.terbangin.presentation.biodata.OrderBiodataActivity
import com.arkan.terbangin.utils.formatDateHourString
import com.arkan.terbangin.utils.formatDateString
import com.arkan.terbangin.utils.formatMinutes
import com.arkan.terbangin.utils.navigateToLogin
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
        bindView()
    }

    private fun setAppBarTitle() {
        binding.layoutAppBar.tvAppbarTitle.text = getString(R.string.appbar_title_rincian_penerbangan)
    }

    private fun setClickListener() {
        binding.layoutTotalPrice.btnChoose.setOnClickListener {
            if (viewModel.isLoggedIn == null) {
                navigateToLogin()
            } else {
//                TicketSoldOutBottomSheet().show(supportFragmentManager, null)
                navigateToOrderBiodata(
                    viewModel.flight!!,
                    viewModel.params!!,
                    viewModel.totalPrice!!,
                )
            }
            // else
            // TODO
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
        binding.tvFlightDuration.text = formatMinutes(viewModel.flight?.duration!!)
        binding.layoutTotalPrice.tvTotalPrice.text = viewModel.totalPrice.toIndonesianFormat()
        binding.layoutCheckoutDetail.tvTakeoffTime.text = formatDateHourString(viewModel.flight?.departureAt!!)
        binding.layoutCheckoutDetail.tvTakeoffDate.text = formatDateString(viewModel.flight?.departureAt!!)
        binding.layoutCheckoutDetail.tvAirportOrigin.text =
            getString(
                R.string.binding_airport_origin_detail,
                viewModel.flight?.startAirportName,
                viewModel.flight?.startAirportTerminal,
            )
        binding.layoutCheckoutDetail.tvAirlineName.text =
            getString(
                R.string.binding_airline_name_detail,
                viewModel.flight?.airlineName,
                viewModel.params?.ticketClass?.name,
            )
        binding.layoutCheckoutDetail.tvAirlineCode.text = viewModel.flight?.airlineSerialNumber
        binding.layoutCheckoutDetail.tvBaggageCapacity.text =
            getString(
                R.string.binding_baggage_capacity_detail,
                viewModel.flight?.airlineBaggage.toString(),
            )
        binding.layoutCheckoutDetail.tvCabinBaggageCapacity.text =
            getString(
                R.string.binding_cabin_baggage_capacity_detail,
                viewModel.flight?.airlineCabinBaggage.toString(),
            )
        binding.layoutCheckoutDetail.tvMoreService.text = viewModel.flight?.airlineAdditionals
        binding.layoutCheckoutDetail.tvLandingTime.text = formatDateHourString(viewModel.flight?.arrivalAt!!)
        binding.layoutCheckoutDetail.tvLandingDate.text = formatDateString(viewModel.flight?.arrivalAt!!)
        binding.layoutCheckoutDetail.tvAirportDestination.text =
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
        const val EXTRA_TOTAL_PRICE = "EXTRA_TOTAL_PRICE"

        fun startActivity(
            context: Context,
            params: FlightSearchParams,
            flight: Flight,
            totalPrice: Double,
        ) {
            val intent = Intent(context, FlightDetailActivity::class.java)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, params)
            intent.putExtra(EXTRA_FLIGHT, flight)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            context.startActivity(intent)
        }
    }
}
