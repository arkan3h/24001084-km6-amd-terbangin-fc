package com.arkan.terbangin.presentation.checkout.selectpassengerseat.seatbookview

import android.view.View

interface SeatLongClickListener {
    fun onAvailableSeatLongClick(view: View)

    fun onBookedSeatLongClick(view: View)

    fun onReservedSeatLongClick(view: View)
}
