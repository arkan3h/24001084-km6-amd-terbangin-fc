package com.arkan.terbangin.presentation.checkout.selectpassengerseat.seatbookview

import android.view.View

interface SeatClickListener {
    fun onAvailableSeatClick(
        selectedIdList: List<Int>,
        view: View,
    )

    fun onBookedSeatClick(view: View)

    fun onReservedSeatClick(view: View)
}
