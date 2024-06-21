package com.arkan.terbangin.presentation.checkout.passengerbiodata

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams
import com.arkan.terbangin.data.model.PassengerBioData
import com.arkan.terbangin.data.repository.passenger.PassengerRepository
import com.arkan.terbangin.data.source.pref.UserPreference
import kotlinx.coroutines.Dispatchers

class PassengerBioDataViewModel(
    extras: Bundle?,
    private val preference: UserPreference,
    private val repository: PassengerRepository,
) : ViewModel() {
    val params = extras?.getParcelable<FlightSearchParams>(PassengerBioDataActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val flight = extras?.getParcelable<Flight>(PassengerBioDataActivity.EXTRA_FLIGHT)
    val totalPrice = extras?.getDouble(PassengerBioDataActivity.EXTRA_TOTAL_PRICE)
    val userId = preference.getUserID()

    private val _createdPassengers = MutableLiveData<List<PassengerBioData>>()
    val createdPassengers: LiveData<List<PassengerBioData>> get() = _createdPassengers
    val passengersList = mutableListOf<PassengerBioData>()

    fun createPassenger(passengerBioData: PassengerBioData) = repository.createPassenger(passengerBioData).asLiveData(Dispatchers.IO)

    fun setPassenger() {
        _createdPassengers.value = passengersList
    }
}
