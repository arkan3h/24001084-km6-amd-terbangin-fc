package com.arkan.terbangin.presentation.checkout.detail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.PassengerBioDataList
import com.arkan.terbangin.data.model.SeatList
import com.arkan.terbangin.data.repository.payment.PaymentRepository
import com.arkan.terbangin.data.source.pref.UserPreference
import kotlinx.coroutines.Dispatchers

class CheckoutDetailViewModel(
    extras: Bundle?,
    preference: UserPreference,
    private val repository: PaymentRepository,
) : ViewModel() {
    private val price = extras?.getDouble(CheckoutDetailActivity.EXTRA_TOTAL_PRICE)
    val flight = extras?.getParcelable<Flight>(CheckoutDetailActivity.EXTRA_FLIGHT)
    val flightReturn = extras?.getParcelable<Flight>(CheckoutDetailActivity.EXTRA_FLIGHT_RETURN)
    val params = extras?.getParcelable<FlightSearchParams>(CheckoutDetailActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val passenger = extras?.getParcelable<PassengerBioDataList>(CheckoutDetailActivity.EXTRA_PASSENGER_DATA)
    val seat = extras?.getParcelable<SeatList>(CheckoutDetailActivity.EXTRA_SEAT)
    val seatReturn = extras?.getParcelable<SeatList>(CheckoutDetailActivity.EXTRA_SEAT_RETURN)
    val userId = preference.getUserID()
    var totalPrice = 0.0
    var adultPrice: Double? = null
    var childrenPrice: Double? = null
    val babyPrice = 0.0
    var taxPrice = 0.0

    fun calculatePrice() {
        val totalPriceEx = (price!! * (params?.totalQty!! - params.babyQty))
        adultPrice = (price * params.adultQty)
        childrenPrice = (price * params.childrenQty)
        taxPrice = totalPriceEx * 0.01
        totalPrice = totalPriceEx + taxPrice
    }

    fun createPayment(
        totalPrice: Int,
        status: String = params?.status!!,
        passengerId: List<String> = passenger?.list?.map { it.id!! }!!,
        seatId: List<String> = seat?.list?.map { it.id }!!,
        seatReturnId: List<String> = seatReturn?.list?.map { it.id }!!,
    ) = repository.createPayment(totalPrice, status, passengerId, seatId, seatReturnId).asLiveData(Dispatchers.IO)
}
