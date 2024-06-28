package com.arkan.terbangin.presentation.history.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import com.arkan.terbangin.R
import com.arkan.terbangin.data.model.History
import com.arkan.terbangin.databinding.ItemSectionDataHistoryBinding
import com.arkan.terbangin.databinding.ItemSectionHeaderHistoryBinding
import com.xwray.groupie.viewbinding.BindableItem

class MonthHeaderItem(private val month: String) : BindableItem<ItemSectionHeaderHistoryBinding>() {
    override fun bind(
        viewBinding: ItemSectionHeaderHistoryBinding,
        position: Int,
    ) {
        viewBinding.tvSectionHeaderName.text = month
    }

    override fun getLayout() = R.layout.item_section_header_history

    override fun initializeViewBinding(view: View): ItemSectionHeaderHistoryBinding {
        return ItemSectionHeaderHistoryBinding.bind(view)
    }
}

class HistoryDataItem(private val data: History) : BindableItem<ItemSectionDataHistoryBinding>() {
    override fun bind(
        viewBinding: ItemSectionDataHistoryBinding,
        position: Int,
    ) {
        setBackgroundStatus(data.bookingStatus, viewBinding)
        viewBinding.tvOrderStatus.text = data.bookingStatus.capitalizeFirstLetter()
        viewBinding.tvLocationFrom.text = data.flight.departureAirport.airportCity
        viewBinding.tvLocationDestination.text = data.flight.arrivalAirport.airportCity
        viewBinding.tvDate.text = data.flight.departureTime.toCompleteDateFormat()
        viewBinding.tvDateDestination.text = data.flight.arrivalTime.toCompleteDateFormat()
        viewBinding.tvTime.text = data.flight.departureTime.toTimeFormat()
        viewBinding.tvTimeDestination.text = data.flight.departureTime.toTimeFormat()
        viewBinding.tvJourneyTime.text = data.duration
        viewBinding.bookingCode.text = data.bookingCode
        viewBinding.classSeat.text = data.classes.capitalizeFirstLetter()
        viewBinding.tvTotalHistory.text = data.totalAmount.toCurrencyFormat()
    }

    override fun getLayout() = R.layout.item_section_data_history

    override fun initializeViewBinding(view: View): ItemSectionDataHistoryBinding {
        return ItemSectionDataHistoryBinding.bind(view)
    }

    private fun setBackgroundStatus(
        status: String,
        viewBinding: ItemSectionDataHistoryBinding,
    ) {
        if (status == "issued") {
            viewBinding.tvOrderStatus.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#73CA5C"))
        } else if (status == "unpaid") {
            viewBinding.tvOrderStatus.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FF0000"))
        }
    }
}
