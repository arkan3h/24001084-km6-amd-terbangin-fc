package com.arkan.terbangin.presentation.checkout.orderbiodata

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.repository.pref.UserPreferenceRepository
import com.arkan.terbangin.data.repository.profile.ProfileRepository
import kotlinx.coroutines.Dispatchers

class OrderBiodataViewModel(
    extras: Bundle?,
    private val pref: UserPreferenceRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {
    val params = extras?.getParcelable<FlightSearchParams>(OrderBiodataActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val flight = extras?.getParcelable<Flight>(OrderBiodataActivity.EXTRA_FLIGHT)
    val flightReturn = extras?.getParcelable<Flight>(OrderBiodataActivity.EXTRA_FLIGHT_RETURN)
    val totalPrice = extras?.getDouble(OrderBiodataActivity.EXTRA_TOTAL_PRICE)

    fun getUserID() = pref.getUserID()

    fun getProfile(id: String) = profileRepository.getProfile(id).asLiveData(Dispatchers.IO)
}
