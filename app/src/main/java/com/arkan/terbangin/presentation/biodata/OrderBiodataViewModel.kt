package com.arkan.terbangin.presentation.biodata

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.arkan.terbangin.data.model.Flight
import com.arkan.terbangin.data.model.FlightSearchParams

class OrderBiodataViewModel(
    extras: Bundle?,
) : ViewModel() {
    val params = extras?.getParcelable<FlightSearchParams>(OrderBiodataActivity.EXTRA_FLIGHT_SEARCH_PARAMS)
    val flight = extras?.getParcelable<Flight>(OrderBiodataActivity.EXTRA_FLIGHT)
    val totalPrice = extras?.getDouble(OrderBiodataActivity.EXTRA_TOTAL_PRICE)
}
