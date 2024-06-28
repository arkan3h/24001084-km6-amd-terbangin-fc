package com.arkan.terbangin.presentation.checkout.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import coil.load
import com.arkan.terbangin.R
import com.arkan.terbangin.base.BaseActivity
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.PassengerBioDataList
import com.arkan.terbangin.data.model.SeatList
import com.arkan.terbangin.databinding.ActivityCheckoutDetailBinding
import com.arkan.terbangin.presentation.checkout.payment.PaymentActivity
import com.arkan.terbangin.utils.formatDateHourString
import com.arkan.terbangin.utils.formatDateString
import com.arkan.terbangin.utils.formatHours
import com.arkan.terbangin.utils.proceedWhen
import com.arkan.terbangin.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckoutDetailActivity : BaseActivity() {
    private val binding: ActivityCheckoutDetailBinding by lazy {
        ActivityCheckoutDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutDetailViewModel by viewModel {
        parametersOf(intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.calculatePrice()
        setAppBarTitle()
        setClickListener()
        bindView()
    }

    private fun setAppBarTitle() {
        binding.layoutAppBar.tvAppbarTitle.text = getString(R.string.appbar_title_rincian_penerbangan)
    }

    private fun setClickListener() {
        binding.layoutAppBar.ibBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.layoutTotalPrice.btnContinuePayment.setOnClickListener {
//            navigateToPayment("https://google.com")
            createPayment(viewModel.totalPrice.toInt())
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
        binding.layoutCheckoutDetail.ivLogoAirline.load(viewModel.flight?.airlinePicture)
        binding.layoutPricingDetailsList.tvTaxTitle.text =
            getString(
                R.string.text_tax_qty,
            )
        binding.layoutPricingDetailsList.tvTaxPrice.text = viewModel.taxPrice.toIndonesianFormat()
        if (viewModel.params?.adultQty != 0) {
            binding.layoutPricingDetailsList.tvAdultPrice.text = viewModel.adultPrice.toIndonesianFormat()
            binding.layoutPricingDetailsList.tvAdultTitle.text =
                getString(
                    R.string.text_adult_qty,
                    viewModel.params?.adultQty.toString(),
                )
        }
        if (viewModel.params?.childrenQty != 0) {
            binding.layoutPricingDetailsList.tvChildrenPrice.text = viewModel.childrenPrice.toIndonesianFormat()
            binding.layoutPricingDetailsList.tvChildrenTitle.text =
                getString(
                    R.string.text_children_qty,
                    viewModel.params?.childrenQty.toString(),
                )
        }
        if (viewModel.params?.babyQty != 0) {
            binding.layoutPricingDetailsList.tvBabyPrice.text = viewModel.babyPrice.toIndonesianFormat()
            binding.layoutPricingDetailsList.tvBabyTitle.text =
                getString(
                    R.string.text_baby_qty,
                    viewModel.params?.babyQty.toString(),
                )
        }
        if (viewModel.params?.status == "Return") {
            binding.tvFlightReturnDestination.text =
                getString(
                    R.string.binding_flight_destination_detail,
                    viewModel.flightReturn?.startAirportCity,
                    viewModel.flightReturn?.endAirportCity,
                )
            binding.tvFlightReturnDuration.text = formatHours(viewModel.flightReturn?.duration!!)
            binding.layoutFlightReturnDetail.tvTakeoffTime.text = formatDateHourString(viewModel.flightReturn?.departureAt!!)
            binding.layoutFlightReturnDetail.tvTakeoffDate.text = formatDateString(viewModel.flightReturn?.departureAt!!)
            binding.layoutFlightReturnDetail.tvAirportOrigin.text =
                getString(
                    R.string.binding_airport_origin_detail,
                    viewModel.flightReturn?.startAirportName,
                    viewModel.flightReturn?.startAirportTerminal,
                )
            binding.layoutFlightReturnDetail.tvAirlineName.text =
                getString(
                    R.string.binding_airline_name_detail,
                    viewModel.flightReturn?.airlineName,
                    viewModel.params?.ticketClass?.name,
                )
            binding.layoutFlightReturnDetail.tvAirlineCode.text = viewModel.flightReturn?.airlineSerialNumber
            binding.layoutFlightReturnDetail.tvBaggageCapacity.text =
                getString(
                    R.string.binding_baggage_capacity_detail,
                    viewModel.flightReturn?.airlineBaggage.toString(),
                )
            binding.layoutFlightReturnDetail.tvCabinBaggageCapacity.text =
                getString(
                    R.string.binding_cabin_baggage_capacity_detail,
                    viewModel.flightReturn?.airlineCabinBaggage.toString(),
                )
            binding.layoutFlightReturnDetail.tvMoreService.text = viewModel.flightReturn?.airlineAdditionals
            binding.layoutFlightReturnDetail.tvLandingTime.text = formatDateHourString(viewModel.flightReturn?.arrivalAt!!)
            binding.layoutFlightReturnDetail.tvLandingDate.text = formatDateString(viewModel.flightReturn?.arrivalAt!!)
            binding.layoutFlightReturnDetail.tvAirportDestination.text =
                getString(
                    R.string.binding_airport_destination_origin,
                    viewModel.flightReturn?.endAirportName,
                    viewModel.flightReturn?.endAirportTerminal,
                )
            binding.layoutFlightReturnDetail.ivLogoAirline.load(viewModel.flightReturn?.airlinePicture)
        }
    }

    private fun createPayment(totalPrice: Int) {
        viewModel.createPayment(totalPrice).observe(this) { it ->
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    it.payload?.data?.snapLink?.let { link ->
                        navigateToPayment(link)
                    }
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

    private fun navigateToPayment(snapLink: String) {
        PaymentActivity.startActivity(this, snapLink)
    }

    companion object {
        const val EXTRA_TOTAL_PRICE = "EXTRA_TOTAL_PRICE"
        const val EXTRA_FLIGHT = "EXTRA_FLIGHT"
        const val EXTRA_FLIGHT_RETURN = "EXTRA_FLIGHT_RETURN"
        const val EXTRA_FLIGHT_SEARCH_PARAMS = "EXTRA_FLIGHT_SEARCH_PARAMS"
        const val EXTRA_PASSENGER_DATA = "EXTRA_PASSENGER_DATA"
        const val EXTRA_SEAT = "EXTRA_SEAT"

        fun startActivity(
            context: Context,
            totalPrice: Double,
            flight: Flight,
            flightReturn: Flight?,
            params: FlightSearchParams,
            passengerData: PassengerBioDataList,
            seats: SeatList,
        ) {
            val intent = Intent(context, CheckoutDetailActivity::class.java)
            intent.putExtra(EXTRA_TOTAL_PRICE, totalPrice)
            intent.putExtra(EXTRA_FLIGHT, flight)
            intent.putExtra(EXTRA_FLIGHT_RETURN, flightReturn)
            intent.putExtra(EXTRA_FLIGHT_SEARCH_PARAMS, params)
            intent.putExtra(EXTRA_PASSENGER_DATA, passengerData)
            intent.putExtra(EXTRA_SEAT, seats)
            context.startActivity(intent)
        }
    }
}
